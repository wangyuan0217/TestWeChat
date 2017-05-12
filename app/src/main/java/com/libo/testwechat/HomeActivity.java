package com.libo.testwechat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.testwechat.http.Apis;
import com.libo.testwechat.http.MyCallback;
import com.libo.testwechat.util.AudioPlayer;
import com.libo.testwechat.util.PreferenceUtil;
import com.libo.testwechat.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Activity implements Switch.OnCheckedChangeListener {
    private Switch mSwitch;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        mSwitch = (Switch) findViewById(R.id.switchON_off);
        mSwitch.setOnCheckedChangeListener(this);
        mSwitch.setChecked(PreferenceUtil.getInstance().getString(Constant.STATUS, "").equals("1"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.layout_error).setVisibility(Utils.isAccessibilitySettingsOn(this) ? View.GONE : View.VISIBLE);
        ((TextView) findViewById(R.id.balance)).setText(PreferenceUtil.getInstance().getString(Constant.BALANCE, "0"));
        int code = PreferenceUtil.getInstance().getInt(Constant.CURRENT, 0);
        long time = PreferenceUtil.getInstance().getLong(Constant.TIME, 0);
        if (System.currentTimeMillis() - time < 2000) {
            if (code == 666) {
                mVibrator.vibrate(new long[]{100, 5000}, 0);
                showAlertDialog(PreferenceUtil.getInstance().getString(Constant.CURRENT_TIP, ""));
            }
        }
    }

    class PlayThread extends Thread {
        private volatile boolean flag = true;

        public void stopTask() {
            flag = false;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                if (flag) {
                    AudioPlayer.getInstance().playMusic();
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private PlayThread mPlayThread;

    public void showAlertDialog(String message) {
        if (mPlayThread == null)
            mPlayThread = new PlayThread();
        mPlayThread.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mPlayThread.stopTask();
                if (mVibrator != null)
                    mVibrator.cancel();
            }

        });
        builder.create().show();
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }

        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.create().show();
    }

    /**
     * 屏蔽返回键
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void open(View view) {
        Utils.openAccessibilitySettings(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isPressed()) return;
        if (isChecked)
            setOn();
        else
            setOff();
    }

    public void setOn() {
        String uid = PreferenceUtil.getInstance().getString(Constant.UID, "");
        if (TextUtils.isEmpty(uid)) return;
        Apis.getInstance().setOn(uid, new MyCallback() {
            @Override
            public void responeData(String body, JSONObject json) {
                mVibrator.vibrate(1500);
                PreferenceUtil.getInstance().put(Constant.STATUS, "1");
                Toast.makeText(App.getInstance(), "开启成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responeDataFail(int responseStatus, String errMsg) {
                mVibrator.vibrate(1500);
                Toast.makeText(App.getInstance(), "异常", Toast.LENGTH_SHORT).show();
                //由关到开  失败则还是关
                mSwitch.setChecked(false);
            }
        });
    }

    public void setOff() {
        String uid = PreferenceUtil.getInstance().getString(Constant.UID, "");
        if (TextUtils.isEmpty(uid)) return;
        Apis.getInstance().setOff(uid, new MyCallback() {
            @Override
            public void responeData(String body, JSONObject json) {
                mVibrator.vibrate(1500);
                PreferenceUtil.getInstance().put(Constant.STATUS, "2");
                Toast.makeText(App.getInstance(), "关闭成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responeDataFail(int responseStatus, String errMsg) {
                mVibrator.vibrate(1500);
                Toast.makeText(App.getInstance(), "异常", Toast.LENGTH_SHORT).show();
                //由开到关  失败则还是开
                mSwitch.setChecked(true);
            }
        });
    }

    public void logout(View view) {
        String uid = PreferenceUtil.getInstance().getString(Constant.UID, "");
        Apis.getInstance().setOff(uid, new MyCallback() {
            @Override
            public void responeData(String body, JSONObject json) {
                mSwitch.setChecked(false);
                App.getInstance().setLogin(false);
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(intent);
                finish();
            }

            @Override
            public void responeDataFail(int responseStatus, String errMsg) {
                Toast.makeText(App.getInstance(), "异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refresh(View view) {
        findViewById(R.id.layout_error).setVisibility(Utils.isAccessibilitySettingsOn(this) ? View.GONE : View.VISIBLE);

        String uid = PreferenceUtil.getInstance().getString(Constant.UID, "");
        Apis.getInstance().getUserInfo(uid, new MyCallback() {
            @Override
            public void responeData(String body, JSONObject json) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    PreferenceUtil.getInstance().put(Constant.BILL_NAME, jsonObject.optString("billname"));
                    PreferenceUtil.getInstance().put(Constant.STATUS, jsonObject.optString("status"));
                    PreferenceUtil.getInstance().put(Constant.BALANCE, jsonObject.optString("balance"));
                    mSwitch.setChecked("1".equals(jsonObject.optString("status")));
                    ((TextView) findViewById(R.id.balance)).setText(jsonObject.optString("balance"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responeDataFail(int responseStatus, String errMsg) {
                Toast.makeText(App.getInstance(), "异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
