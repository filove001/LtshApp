package com.ltsh.app.chat.service;

import android.os.Handler;

import com.ltsh.app.chat.CallbackInterface;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.TimerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Random on 2018/1/10.
 */

public class LoadDataTime {
    List<TimerUtils> times = new ArrayList<>();
    public void start(final Runnable runnable, Handler handler) {
        TimerUtils timerUtils = new TimerUtils(handler, 2000L) {
            @Override
            public void execute() {
                runnable.run();
            }
        };
        times.add(timerUtils);
        timerUtils.start();
    }
    public void stopAll() {

        for (TimerUtils timerUtils : times) {
            timerUtils.stop();
        }
    }
}
