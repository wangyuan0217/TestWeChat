package com.libo.testwechat;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/27.
 */

public class Utils {

    /**
     * 系统是否在锁屏状态
     *
     * @return
     */
    public static boolean isScreenLocked(Service service) {
        KeyguardManager keyguardManager = (KeyguardManager) service.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public static int getScreenWidth(Service service) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = service.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Service service) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = service.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    // 此方法用来判断当前应用的辅助功能服务是否开启
    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    public static void openAccessibilitySettings(Context context) {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(context)) {
            // 引导至辅助功能设置页面
            context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } else {
            // 执行辅助功能服务相关操作
        }
    }

}
