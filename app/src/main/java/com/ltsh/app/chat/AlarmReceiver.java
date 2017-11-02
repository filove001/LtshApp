package com.ltsh.app.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ltsh.app.chat.service.ReceiveMsgService;

/**
 * Created by Random on 2017/9/27.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,ReceiveMsgService.class);
        context.startService(i);
    }
}