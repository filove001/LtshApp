package com.ltsh.app.chat.config;

import android.content.Context;
import android.os.Handler;

import com.ltsh.app.chat.adapter.ChatAdapter;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.adapter.MsgListAdapter;
import com.ltsh.app.chat.db.DBCipherHelper;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.utils.cache.DiskLruCache;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.common.util.http.OkHttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Random on 2017/9/27.
 */

public class CacheObject {
    public static ChatAdapter chatAdapter = null;
    public static MsgListAdapter msgListAdapter = null;
    public static FriendAdapter friendAdapter = null;
    public static Handler handler;
    public static UserToken userToken = null;
//    public static Map<String, String> commonParams = new HashMap<>();
    public static AppReq baseReq = new AppReq();
    public static Context context;
    public static DBCipherHelper dbHelper;



    public static DiskLruCache diskLruCache;
//    public InputStream getCache(String key) {
//        String newKey = DiskLruCache.Util.hashKeyForDisk(key);
//        try {
//            DiskLruCache.Snapshot snapshot = diskLruCache.get(newKey);
//            if(snapshot != null) {
//                return snapshot.getInputStream(0);
//            } else {
//                DiskLruCache.Editor edit = diskLruCache.edit(newKey);
//                OutputStream outputStream = edit.newOutputStream(0);
//                OkHttpUtils.download(key, outputStream);
//                snapshot = diskLruCache.get(newKey);
//                return snapshot.getInputStream(0);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
