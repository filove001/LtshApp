package com.ltsh.app.chat.times;

import android.app.ProgressDialog;
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

public class LoadDataTimerTask extends BaseTimerTask {
    private Handler handler;
    private ProgressDialog progressDialog;
    public LoadDataTimerTask(Handler handler, ProgressDialog progressDialog) {
        this.handler = handler;
        this.progressDialog = progressDialog;
    }

    private boolean isFast = true;
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
                        if(isFast && progressDialog != null) {
                            isFast = false;
                            progressDialog.cancel();
                        }

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
