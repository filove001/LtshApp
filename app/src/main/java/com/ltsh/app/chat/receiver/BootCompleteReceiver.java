package com.ltsh.app.chat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ltsh.app.chat.service.MsgPushService;
import com.ltsh.common.util.LogUtils;

/**
 * Created by Random on 2018/1/11.
 */

public class BootCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, MsgPushService.class);
        context.startService(service);
        LogUtils.info("Boot Complete. Starting MsgPushService...");
    }

}