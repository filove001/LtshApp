package com.ltsh.app.chat.utils;

import android.content.Context;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.db.MyDBOpenHelper;
import com.ltsh.app.chat.entity.UserToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/9/28.
 */

public class AppInit {

    public static void init(Context context){
        if(DbUtils.dbHelper == null) {
            DbUtils.dbHelper = new MyDBOpenHelper(context, "ltsh-app.db", null, 1);
        }
        CacheObject.commonParams.put("appVersion", "1.0");
        CacheObject.commonParams.put("medium", "123");
        CacheObject.commonParams.put("appId", "app");
        CacheObject.commonParams.put("mediumType", "0");
    }
}
