package com.ltsh.app.chat.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.dao.MessageItemDao;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.viewbean.MessageItem;

import com.ltsh.common.util.LogUtils;

import java.util.List;

/**
 * Created by Random on 2017/9/27.
 */

public class MsgListService extends IntentService {

    private boolean isStart = true;
    public MsgListService(){
        super("com.ltsh.app.chat.MSG_LIST_SERVICE");
    }
    public MsgListService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.info("onBind方法被调用!");
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(isStart) {
            if(CacheObject.messageAdapter != null) {
                final List<MessageItem> messageItemList = MessageItemDao.getList(CacheObject.userToken.getId());
                CacheObject.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CacheObject.messageAdapter.addAll(0,messageItemList);
                    }
                });
            }
            if(CacheObject.friendAdapter != null) {
                final List<UserFriend> userFriendList = BaseDao.query(UserFriend.class, "create_by=?", new String[]{CacheObject.userToken.getId() + ""}, null);
                CacheObject.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CacheObject.friendAdapter.addAll(0,userFriendList);
                    }
                });
            }

            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                LogUtils.error(e.getMessage(), e);
            }
        }

    }

    //Service被创建时调用
    @Override
    public void onCreate() {
        LogUtils.info("onCreate方法被调用!");
        super.onCreate();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isStart = true;
        return super.onStartCommand(intent, flags, startId);

    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        LogUtils.info("onDestory方法被调用!");
        isStart = false;
        super.onDestroy();

    }


}
