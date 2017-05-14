package com.libo.testwechat;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.libo.testwechat.entity.UserInfo;
import com.libo.testwechat.http.Apis;
import com.libo.testwechat.http.MyCallback;
import com.libo.testwechat.util.PreferenceUtil;
import com.libo.testwechat.util.Utils;

import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends Activity {

    private EditText name, pass;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.open).setVisibility(Utils.isAccessibilitySettingsOn(this) ? View.GONE : View.VISIBLE);
        if (PreferenceUtil.getInstance().getBoolean(Constant.NEED_SOUND, false)) {
            PreferenceUtil.getInstance().put(Constant.NEED_SOUND, false);
            int code = PreferenceUtil.getInstance().getInt(Constant.CURRENT, 0);
            if (code == 666) {
                mVibrator.vibrate(1500);
                Toast.makeText(this, PreferenceUtil.getInstance().getString(Constant.CURRENT_TIP, ""), Toast.LENGTH_LONG).show();
            }
        }

        if (getIntent().getBooleanExtra("autoLoginAction", false)) {
            String username = PreferenceUtil.getInstance().getString(Constant.UNAME, "");
            String password = PreferenceUtil.getInstance().getString(Constant.PASSWORD, "");
            name.setText(username);
            pass.setText(password);
            loginAction(true);
        }
    }

    public void open(View view) {
        Utils.openAccessibilitySettings(this);
    }

    public void login(View view) {
        loginAction(false);
    }

    public void loginAction(boolean isAuto) {
        if (!Utils.isAccessibilitySettingsOn(this)) {
            Toast.makeText(this, "请先点击'开启无障碍服务'按钮，在无障碍里面开启本应用", Toast.LENGTH_LONG).show();
            return;
        }

        final String uname = name.getText().toString().trim();
        final String upass = pass.getText().toString().trim();
        if (isEmpty(uname) || TextUtils.isEmpty(upass))
            return;

        Apis.getInstance().login(isAuto ? "2" : "", uname, upass, new MyCallback() {
            @Override
            public void responeData(String body, JSONObject json) {
                Toast.makeText(App.getInstance(), "登录成功", Toast.LENGTH_SHORT).show();
                UserInfo userInfo = new Gson().fromJson(body, new TypeToken<UserInfo>() {
                }.getType());
                App.getInstance().setLogin(true);
                PreferenceUtil.getInstance().put(Constant.UNAME, uname);
                PreferenceUtil.getInstance().put(Constant.PASSWORD, upass);
                PreferenceUtil.getInstance().put(Constant.STATUS, userInfo.getStatus());
                PreferenceUtil.getInstance().put(Constant.USERINFO, userInfo.toString());
                PreferenceUtil.getInstance().put(Constant.KEY, userInfo.getKey());
                PreferenceUtil.getInstance().put(Constant.UID, userInfo.getId());
                PreferenceUtil.getInstance().put(Constant.BILL_NAME, userInfo.getBillname());
                PreferenceUtil.getInstance().put(Constant.BALANCE, userInfo.getBalance());

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }

            @Override
            public void responeDataFail(int responseStatus, String errMsg) {
                if (responseStatus == 400)
                    Toast.makeText(App.getInstance(), errMsg, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(App.getInstance(), "异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
