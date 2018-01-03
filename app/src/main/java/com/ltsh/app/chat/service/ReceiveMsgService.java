package com.ltsh.app.chat.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;


import com.ltsh.app.chat.CallbackInterface;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.utils.TimerUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.app.chat.config.CacheObject;

import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/9/27.
 */

public class ReceiveMsgService extends TimeService {

    public ReceiveMsgService(){
        super("com.ltsh.app.chat.RECEIVE_MSG_SERVICE");
    }
    public ReceiveMsgService(String name) {
        super(name);
    }

    @Override
    protected void executeTimes() {
        Map<String, Object> params = new HashMap<String, Object>();
        setLock(true);
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_MESSAGE_URL, params, getApplicationContext(), new CallbackInterface() {
            @Override
            public void callBack(Result result) {
                loadData(result);
                setLock(false);
            }

            @Override
            public void error(Result result) {
                setLock(false);
            }
        });

    }

    public void loadData(final Result result) {
        if(ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            Map map = (Map)result.getContent();

            if(map != null) {
                final MessageInfo chatMessage = JsonUtils.fromJson(JsonUtils.toJson(map), MessageInfo.class);
                int id = BaseDao.insert(chatMessage);
                chatMessage.setId(id);
                if(CacheObject.chatAdapter != null) {
                    CacheObject.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CacheObject.chatAdapter.add(chatMessage, true);
                        }
                    });
                }
//                CacheObject.handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        CacheObject.messageAdapter.add(0,chatMessage);
//                    }
//                });
//                chatMessage;
            }
        } else {
            CacheObject.handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), result.getCode() + ":" + result.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
