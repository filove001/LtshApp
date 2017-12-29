package com.ltsh.app.chat.utils;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Random on 2017/12/7.
 */

public abstract class TimerUtils implements Runnable {
    private Handler handler;
    private long times = 2000l;
    public TimerUtils(Handler handler, long times){
        this.handler = handler;
        this.times = times;
    }

    @Override
    public void run() {
        execute();
        handler.postDelayed(this, times);
    }
    public abstract void execute();
    public void start(){
        new Thread(this).start();
    }
    public void stop(){
        handler.removeCallbacks(this);
    }
}
