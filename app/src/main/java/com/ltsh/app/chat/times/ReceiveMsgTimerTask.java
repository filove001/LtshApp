package com.ltsh.app.chat.times;

import android.content.Context;
import android.widget.Toast;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;

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
                Map<String, Object> params = new HashMap<String, Object>();
                AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_MESSAGE_URL, params, context, new CallbackHandler() {
                    @Override
                    public void callBack(Result result) {
                        loadData(result);
                        setLock(false);
                    }

                    @Override
                    public void error(Result result) {
                        setLock(false);
                        if(result.getCode().equals(ResultCodeEnum.TOKEN_FAIL.getCode())) {
                            cancel();
                        }
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
                            CacheObject.chatAdapter.add(chatMessage, true);
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
