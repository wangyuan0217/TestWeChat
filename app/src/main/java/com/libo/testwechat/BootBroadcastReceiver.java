package com.libo.testwechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_BOOT)) {
            Intent i = new Intent(context, AutoReplyService.class);
            context.startService(i);
        }
    }
}
