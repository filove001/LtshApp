package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;


import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.req.MessageSendReq;
import com.ltsh.app.chat.enums.StatusEnums;
import com.ltsh.app.chat.entity.UserFriend;
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
        BaseDao.insert(messageInfo);
        messageInfo.setSourceId(CacheObject.userToken.getId() + "");


        CacheObject.chatAdapter.add(messageInfo, false);
        MessageSendReq req = new MessageSendReq();
        BeanUtils.copyProperties(messageInfo, req);
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.SEND_MESSAGE_URL, JsonUtils.fromJson(JsonUtils.toJson(req),Map.class), activity, null);
        edSendMsg.setText("");
    }

}
