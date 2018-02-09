package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;


import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.entity.req.MessageSendReq;
import com.ltsh.app.chat.enums.StatusEnums;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.handler.impl.DefaultCallbackHandler;
import com.ltsh.app.chat.service.MessageService;
import com.ltsh.app.chat.utils.ServiceContextUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.app.chat.utils.BeanUtils;

import com.ltsh.common.util.JsonUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by Random on 2017/10/26.
 */

public class SendBtnOnClickListener implements View.OnClickListener {
    private UserFriend userFriend;
    private Activity activity;
    public SendBtnOnClickListener(Activity activity, UserFriend userFriend) {
        this.userFriend = userFriend;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        EditText edSendMsg = (EditText)activity.findViewById(R.id.ed_send_msg_input);
        String sendMessage = edSendMsg.getText().toString();
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsgContext(sendMessage);
        messageInfo.setToUser(userFriend.getFriendUserId());
//        messageInfo.setToUserName(userFriend.getName());
        messageInfo.setMsgType(0);
        messageInfo.setSendType(0);
        messageInfo.setCreateBy(CacheObject.userToken.getId());
//        messageInfo.setCreateByName(CacheObject.userToken.getName());
        messageInfo.setCreateTime(new Date());
        messageInfo.setStatus(StatusEnums.YFS.getValue());
        messageInfo.setSourceType("USER");

        messageInfo.setSourceId(CacheObject.userToken.getId() + "");

        MessageService messageService = ServiceContextUtils.getService(MessageService.class);
        messageService.sendMsg(messageInfo, new DefaultCallbackHandler(){
            /**
             * 请求之前
             * @param appReq
             */
            public void before(final AppReq appReq) {
                CacheObject.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MessageInfo messageInfo = (MessageInfo)appReq.getContent();
                        BaseDao.insert(messageInfo);
                        CacheObject.chatAdapter.add(messageInfo, false);
                    }
                });

            }
        });

        edSendMsg.setText("");
    }

}
