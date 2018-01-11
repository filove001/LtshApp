package com.ltsh.app.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ltsh.common.util.LogUtils;

/**
 * Created by Random on 2018/1/11.
 */

public class MsgPushService extends Service {

    private static final String TAG = "MsgPushService";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.info(TAG, "onCreate called.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.info("onStartCommand called.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}