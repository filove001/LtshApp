package com.ltsh.app.chat.times;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.dao.MessageItemDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.viewbean.MessageItem;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by Random on 2018/1/16.
 */

public class LoadDataTimerTask extends BaseTimerTask {
    private Handler handler;
    public LoadDataTimerTask(Handler handler) {
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        if(CacheObject.userToken != null) {
            if(CacheObject.msgListAdapter != null && CacheObject.userToken != null) {
                final List<MessageItem> messageItemList = MessageItemDao.getList(CacheObject.userToken.getId());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CacheObject.msgListAdapter.addAll(0,messageItemList);
                    }
                });
            }
            if(CacheObject.friendAdapter != null && CacheObject.userToken != null) {
                final List<UserFriend> userFriendList = BaseDao.query(UserFriend.class, "belongs_to=?", new String[]{CacheObject.userToken.getId() + ""}, null);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CacheObject.friendAdapter.addAll(0,userFriendList);
                    }
                });
            }
        }

    }
}
