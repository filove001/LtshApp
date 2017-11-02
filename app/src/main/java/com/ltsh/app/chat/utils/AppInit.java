package com.ltsh.app.chat.utils;

import android.content.Context;

import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.db.MyDBOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/9/28.
 */

public class AppInit {
    public static Map<String, String> commonParams = new HashMap<>();
    public static void init(Context context){
        if(DbUtils.dbHelper == null) {
            DbUtils.dbHelper = new MyDBOpenHelper(context, "ltsh-app.db", null, 1);
        }

        commonParams.put("appVersion", "1.0");
        commonParams.put("medium", "123");
        commonParams.put("appId", "app");
        commonParams.put("mediumType", "0");
    }
}
