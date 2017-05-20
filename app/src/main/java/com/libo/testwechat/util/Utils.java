package com.libo.testwechat.util;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class Utils {

    public static String findLastWord(String content) {
        StringBuilder returnStr = new StringBuilder();
        int size = content.length();
        for (int i = 0; i < size; i++) {
            try {
                int temp = Integer.parseInt(content.charAt(i) + "");
                returnStr.append(temp);
            } catch (Exception e) {

            }
        }
        int length = returnStr.length();
        if (length < 3)
            return "";
        return returnStr.toString().substring(length - 2, length);
    }

    /**
     * ======风云会演说家32: 2140377期：
     * 02+07+01=10(小双)
     * 近 10 期：
     * 10 16 06 10 09 16 08 05 15 10
     *
     * @param content
     * @return
     */
    public static String findPhase(String content) {
        StringBuilder returnStr = new StringBuilder();
        int index = content.indexOf(":");
        for (int i = index + 1; i < content.length(); i++) {

            String str = content.charAt(i) + "";
            if (TextUtils.isEmpty(str)) continue;
            if ("期".equals(str)) break;

            returnStr.append(str);
        }
        return returnStr.toString();
    }

    public static String findBill(String key, String content) {
        StringBuilder returnStr = new StringBuilder();
        int lengthKey = key.length();
        int contentSize = content.length();
        if (content.contains(key)) {
            int next = content.indexOf(key) + lengthKey;
            boolean isFirst = true;
            for (int i = next; i < contentSize; i++) {
                try {
                    int temp;
                    temp = Integer.parseInt(content.charAt(i) + "");
                    returnStr.append(temp);
                    isFirst = false; //只要遇到能强转成功则
                } catch (Exception e) {
                    if (isFirst) {
                        isFirst = false;
                        continue;
                    }
                    return returnStr.toString();
                }
            }
        }
        return returnStr.toString();
    }

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

    public static int getScreenWidth(Context service) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = service.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context service) {
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
