package com.ltsh.app.chat.times;

import android.os.Handler;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.db.MessageItemDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.viewbean.MessageItem;

import java.util.List;

/**
 * Created by Random on 2018/1/16.
 */

public class ChatTimerTask extends BaseTimerTask {
    private Handler handler;
    private UserFriend userFriend;
    public ChatTimerTask(Handler handler, UserFriend userFriend) {
        this.handler = handler;
        this.userFriend = userFriend;
    }


    @Override
    public void run() {
        super.run();
        if(CacheObject.userToken != null) {
            if(CacheObject.chatAdapter != null && CacheObject.userToken != null) {
                final List<MessageInfo> list = BaseDao.query(MessageInfo.class, "(create_by=?)and belongs_to=?", new String[]{userFriend.getFriendUserId() + "", CacheObject.userToken.getId() + ""}, null);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CacheObject.chatAdapter.addAll(0,list);
                    }
                });
            }
        }

    }
}
