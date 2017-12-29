package com.ltsh.app.chat.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.dao.MessageItemDao;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.viewbean.MessageItem;

import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Random on 2017/9/27.
 */

public class LoadDataService extends TimeService {


    public LoadDataService(){
        super("com.ltsh.app.chat.MSG_LIST_SERVICE");
    }
    public LoadDataService(String name) {
        super(name);
    }

    @Override
    protected void executeTimes() {
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
    }

}
