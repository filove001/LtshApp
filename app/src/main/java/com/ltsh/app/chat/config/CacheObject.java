package com.ltsh.app.chat.config;

import android.os.Handler;
import android.util.LruCache;

import com.ltsh.app.chat.adapter.ChatAdapter;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.adapter.MsgListAdapter;
import com.ltsh.app.chat.db.DBCipherHelper;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.UserGroup;
import com.ltsh.app.chat.utils.DiskLruCache;
import com.ltsh.app.chat.utils.db.MyDBOpenHelper;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.utils.http.OkHttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import okhttp3.internal.io.FileSystem;


/**
 * Created by Random on 2017/9/27.
 */

public class CacheObject {
    public static ChatAdapter chatAdapter = null;
    public static MsgListAdapter msgListAdapter = null;
    public static FriendAdapter friendAdapter = null;
    public static Handler handler;
    public static UserToken userToken = null;
    public static Map<String, String> commonParams = new HashMap<>();
    public static DBCipherHelper dbCipherHelper;
    public static DiskLruCache diskLruCache;
    public InputStream getCache(String key) {
        String newKey = DiskLruCache.Util.hashKeyForDisk(key);
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(newKey);
            if(snapshot != null) {
                return snapshot.getInputStream(0);
            } else {
                DiskLruCache.Editor edit = diskLruCache.edit(newKey);
                OutputStream outputStream = edit.newOutputStream(0);
                OkHttpUtils.download(key, outputStream);
                snapshot = diskLruCache.get(newKey);
                FileSystem system = FileSystem.SYSTEM;
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
