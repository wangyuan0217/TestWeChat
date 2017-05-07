package com.libo.testwechat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.open).setVisibility(Utils.isAccessibilitySettingsOn(this) ? View.GONE : View.VISIBLE);
    }

    public void open(View view) {
        Utils.openAccessibilitySettings(this);

//        RootShellCmd.simulateKey(500, 1570);

    }

}
