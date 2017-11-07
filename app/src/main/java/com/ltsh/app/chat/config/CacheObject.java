package com.ltsh.app.chat.config;

import android.os.Handler;

import com.ltsh.app.chat.adapter.ChatAdapter;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.adapter.MessageAdapter;
import com.ltsh.app.chat.utils.db.MyDBOpenHelper;
import com.ltsh.app.chat.entity.UserToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/9/27.
 */

public class CacheObject {
    public static Map<String, Object> cacheMap = new HashMap<>();
    public static ChatAdapter chatAdapter = null;
    public static MessageAdapter messageAdapter = null;
    public static FriendAdapter friendAdapter = null;
    public static Handler handler;
    public static UserToken userToken = null;
    public static Map<String, String> commonParams = new HashMap<>();
    public static MyDBOpenHelper dbHelper;
}
