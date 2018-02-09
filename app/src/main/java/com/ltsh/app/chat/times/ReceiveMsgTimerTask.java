package com.ltsh.app.chat.times;

import android.content.Context;
import android.widget.Toast;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;

import com.ltsh.app.chat.handler.impl.DefaultCallbackHandler;
import com.ltsh.app.chat.service.MessageService;
import com.ltsh.app.chat.utils.ServiceContextUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2018/1/16.
 */

public class ReceiveMsgTimerTask extends BaseTimerTask {
    private Context context;
    private boolean lock = false;
    public ReceiveMsgTimerTask(Context context) {
        this.context = context;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        super.run();
        if(!isLock()) {
            setLock(true);
            if(CacheObject.userToken != null) {
                MessageService messageService = ServiceContextUtils.getService(MessageService.class);
                messageService.getMsg(new DefaultCallbackHandler() {
                    @Override
                    public void succeed(Result result) {
                        loadData(result);
                    }
                    @Override
                    public void fail(Result result) {
                        if(result.getCode().equals(ResultCodeEnum.TOKEN_FAIL.getCode())) {
                            cancel();
                        }
                    }

                    @Override
                    public void complete(Result result) {
                        setLock(false);
                    }
                });
            }
        }
    }

    public void loadData(final Result result) {
        if(ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            Map map = (Map)result.getContent();
            if(map != null) {
                final MessageInfo chatMessage = JsonUtils.fromJson(JsonUtils.toJson(map), MessageInfo.class);
                BaseDao.insert(chatMessage);
                if(CacheObject.chatAdapter != null) {
                    CacheObject.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CacheObject.chatAdapter.add(chatMessage);
                        }
                    });
                }
            }
        } else {
            CacheObject.handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, result.getCode() + ":" + result.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
