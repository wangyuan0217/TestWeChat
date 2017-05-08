package com.libo.testwechat;

import java.io.File;

/**
 * root权限判断
 */
public class RootUtil {

    // 获取ROOT权限
    public static void get_root() {

        if (is_root()) {
            System.out.println("已Root");
        } else {
            try {
                System.out.println("正在获取ROOT权限");
                Runtime.getRuntime().exec("su");
            } catch (Exception e) {
                System.out.println("获取ROOT权限时出错");
            }
        }

    }

    // 判断是否具有ROOT权限
    public static boolean is_root() {
        boolean res = false;
        try {
            if ((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists())) {
                res = false;
            } else {
                res = true;
            }
        } catch (Exception e) {

        }
        return res;
    }
}
