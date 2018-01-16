package com.ltsh.app.chat.config;

import com.ltsh.common.util.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Random on 2018/1/16.
 */

public class TimerUtils {
    private static Map<String, TimerTask> timerTasks = new HashMap<>();
    private static Timer timer;
    public static Timer getTimerInstance() {
        if(timer == null) {
            synchronized (TimerUtils.class) {
                if(timer == null) {
                    timer = new Timer();
                }
            }
        }
        return timer;
    }
    public static void schedule(Class classTemp,TimerTask timerTask, long delay, long period) {


        String timeKey = classTemp.getSimpleName();
        schedule(timeKey, timerTask, delay, period);
    }
    public static void schedule(String timeKey,TimerTask timerTask, long delay, long period) {
        if(timerTasks.get(timeKey) != null) {
            LogUtils.error("定时器:" + timeKey + "已存在");
        } else {
            getTimerInstance().schedule(timerTask, delay, period);
            timerTasks.put(timeKey, timerTask);
        }
    }
    public static void cancel(String timeKey) {
        TimerTask timerTask = timerTasks.get(timeKey);
        if(timerTask != null) {
            timerTask.cancel();
            timerTasks.remove(timerTask);
        } else {
            LogUtils.error("定时器:" + timeKey + "不存在");
        }
    }
    public static void cancel() {
        timer.cancel();
        timer = null;
        timerTasks.clear();
    }
}
