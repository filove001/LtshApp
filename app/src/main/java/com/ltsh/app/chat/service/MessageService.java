package com.ltsh.app.chat.service;

import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.MessageSendReq;
import com.ltsh.app.chat.handler.CallbackHandler;

/**
 * Created by Random on 2018/1/18.
 */

public interface MessageService extends BaseService {
    /**
     * 发送消息
     */
    public Result sendMsg(MessageInfo messageInfo, CallbackHandler callbackHandler);

    /**
     * 获取消息
     */
    public void getMsg(CallbackHandler callbackHandler);

    /**
     * 发送文件消息
     */
    public void sendFileMsg();
}
