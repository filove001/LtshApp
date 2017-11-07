package com.ltsh.app.chat.config;

import android.content.Context;

import com.ltsh.app.chat.utils.db.MyDBOpenHelper;

/**
 * Created by Random on 2017/9/28.
 */

public class AppInit {

    public static void init(Context context){
        if(CacheObject.dbHelper == null) {
            CacheObject.dbHelper = new MyDBOpenHelper(context, "ltsh-app.db", null, 1);
        }
        CacheObject.commonParams.put("appVersion", "1.0");
        CacheObject.commonParams.put("medium", "123");
        CacheObject.commonParams.put("appId", "app");
        CacheObject.commonParams.put("mediumType", "0");
    }
}
