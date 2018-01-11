package com.ltsh.app.chat.service;

import android.os.Handler;

import com.ltsh.app.chat.handler.InvokeHandler;
import com.ltsh.app.chat.utils.TimerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2018/1/10.
 */

public class LoadDataTime {
    private static Map<String, TimerUtils> times = new HashMap<>();
    public static void start(String id, final InvokeHandler invokeHandler, Handler handler) {
        if(times.get(id) == null) {
            TimerUtils timerUtils = new TimerUtils(handler, 2000L) {
                @Override
                public void execute() {
                    invokeHandler.invoke();
                }
            };
            times.put(id, timerUtils);
            timerUtils.start();
        }

    }
    public static void stopAll() {
        for (String key : times.keySet()) {
            TimerUtils timerUtils = times.get(key);
            timerUtils.stop();
        }
        times.clear();
    }
}
