package com.ltsh.app.chat.utils;

import android.app.Activity;
import android.content.Intent;

import com.ltsh.app.chat.MainActivity;
import com.ltsh.app.chat.activity.BaseActivity;
import com.ltsh.app.chat.activity.LoginActivity;
import com.ltsh.app.chat.config.CacheObject;

import java.util.Set;

/**
 * Created by Random on 2018/1/11.
 */

public class LoginOutUtils {
    public static void loginOut() {
        CacheObject.handler.post(new Runnable() {
            @Override
            public void run() {
                Set<Activity> activitySet = BaseActivity.activitySet;
                Activity mainActivity = null;
                for (Activity activity1 : activitySet) {
                    if(!(activity1 instanceof MainActivity)) {
                        if(!activity1.isFinishing()) {
                            activity1.finish();
                        }
                    } else {
                        mainActivity = activity1;
                    }

                }
                Intent loginIntent = new Intent("android.intent.action.LOGIN");
                loginIntent.setClassName(mainActivity, LoginActivity.class.getName());
                mainActivity.startActivity(loginIntent);
            }
        });
    }
}
