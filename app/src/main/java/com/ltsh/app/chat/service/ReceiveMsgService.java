package com.ltsh.app.chat.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ltsh.app.chat.CallBackInterface;
import com.ltsh.app.chat.MyAlertDiaLog;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.utils.AppHttpClient;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.JsonUtils;
import com.ltsh.app.chat.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/9/27.
 */

public class ReceiveMsgService extends IntentService implements CallBackInterface {

    private boolean isStart = true;
    public ReceiveMsgService(){
        super("com.ltsh.app.chat.RECEIVE_MSG_SERVICE");
    }
    public ReceiveMsgService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i("onBind方法被调用!");
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(isStart) {
            Map<String, Object> params = new HashMap<>();
            AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_MESSAGE_URL, params, this, getApplicationContext());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                LogUtils.e(e.getMessage(), e);
            }
        }

    }

    //Service被创建时调用
    @Override
    public void onCreate() {
        LogUtils.i("onCreate方法被调用!");
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
        LogUtils.i("onDestory方法被调用!");
        isStart = false;
        super.onDestroy();

    }

    @Override
    public void callBack(Result result) {
        if(ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            Map map = (Map)result.getContent();

            if(map != null) {
                final MessageInfo chatMessage = JsonUtils.fromJson(JsonUtils.toJson(map), MessageInfo.class);
                DbUtils.insert(chatMessage);

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
                    Toast.makeText(getApplicationContext(), "默认Toast样式",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}