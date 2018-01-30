package com.ltsh.app.chat.service.impl;

import android.app.Activity;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.entity.req.MessageSendReq;
import com.ltsh.app.chat.entity.req.RandomReq;
import com.ltsh.app.chat.entity.resp.RandomResp;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.service.BasicsService;
import com.ltsh.app.chat.service.MessageService;
import com.ltsh.app.chat.utils.BeanUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.StringUtils;

import java.util.Map;

/**
 * Created by Random on 2018/1/18.
 */

public class MessageServiceImpl extends BaseServiceImpl implements MessageService{

    public MessageServiceImpl(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;
    private BasicsService basicsService = new BasicsServiceImpl();
    /**
     * 发送消息
     */
    public Result sendMsg(MessageInfo messageInfo) {
        AppReq<MessageInfo> appReq = new AppReq<>(messageInfo);

        RandomReq randomReq = new RandomReq();
        randomReq.setUuid(StringUtils.getUUID());
        Result<RandomResp> random = basicsService.getRandom();
        super.threadRequest(AppConstants.SERVLCE_URL, AppConstants.SEND_MESSAGE_URL, appReq, random.getContent(), activity, null);
        return new Result<>(ResultCodeEnum.SUCCESS);
    }



    @Override
    public void getMsg() {

    }


    /**
     * 发送文件消息
     */
    public void sendFileMsg() {}
}
