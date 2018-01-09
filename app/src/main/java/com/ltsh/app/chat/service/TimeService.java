package com.ltsh.app.chat.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ltsh.app.chat.config.CacheObject;

import com.ltsh.app.chat.utils.TimerUtils;

import com.ltsh.common.util.LogUtils;


/**
 * Created by Random on 2017/12/29.
 */

public abstract class TimeService extends IntentService {
    private TimerUtils timerUtils;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TimeService(String name) {
        super(name);
    }
    protected Long getTimes() {
        return 2000L;
    }
    protected abstract void executeTimes();
    private boolean isLock = false;
    private boolean isRun = true;
    protected void setLock(boolean isLock){
        this.isLock = isLock;
    }
    protected boolean getLock(){
        return isLock;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.info("onBind方法被调用!");
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        timerUtils = new TimerUtils(CacheObject.handler, 2000L) {
            @Override
            public void execute() {
                if(!getLock()) {
                    executeTimes();
                }

            }
        };
        timerUtils.start();
        if(CacheObject.handler == null) {
            isRun = false;
        }
        while(isRun) {
            try {
                Thread.sleep(60000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //Service被创建时调用
    @Override
    public void onCreate() {
//        LogUtils.info("onCreate方法被调用!");
        super.onCreate();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        timerUtils.stop();
//        LogUtils.info("onDestory方法被调用!");
        super.onDestroy();

    }
}
