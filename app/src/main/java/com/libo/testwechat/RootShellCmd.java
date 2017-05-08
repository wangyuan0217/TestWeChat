package com.libo.testwechat;

import android.util.Log;

import java.io.File;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * 用root权限执行Linux下的Shell指令
 */
public class RootShellCmd {
    private static OutputStream os;

    /**
     * 执行shell指令 * * @param cmd * 指令
     */
    public static void exec(String cmd) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过keyCode模拟点击
     *
     * @param keyCode
     */
    public static void simulateKey(int keyCode) {
        exec("input keyevent " + keyCode + "\n");
    }

    /**
     * 通过坐标模拟点击
     *
     * @param x
     * @param y
     */
    public static void simulateKey(int x, int y) {
        //利用ProcessBuilder执行shell命令//利用ProcessBuilder执行shell命令
        exec("input tap " + x + " " + y + "\n");
    }

    private static boolean isRooted() {
        return findBinary("su");
    }

    public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = {"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
                    "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
            for (String where : places) {
                Log.d(TAG, "DATA where = " + where);
                if (new File(where + binaryName).exists()) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
}
