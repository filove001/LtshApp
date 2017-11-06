package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;


import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.enums.StatusEnums;
import com.ltsh.app.chat.req.MessageSendReq;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.utils.AppHttpClient;
import com.ltsh.app.chat.utils.BeanUtils;
import com.ltsh.app.chat.utils.DateUtils;
import com.ltsh.app.chat.utils.JsonUtils;

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
        messageInfo.setToUserName(userFriend.getName());
        messageInfo.setMsgType(0);
        messageInfo.setSendType(0);
        messageInfo.setCreateBy(CacheObject.userToken.getId());
        messageInfo.setCreateByName(CacheObject.userToken.getName());
        messageInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
        messageInfo.setStatus(StatusEnums.YFS.getValue());
        int id = DbUtils.insert(messageInfo);
        messageInfo.setId(id);
        CacheObject.chatAdapter.add(messageInfo, true);
        MessageSendReq req = new MessageSendReq();
        BeanUtils.copyProperties(messageInfo, req);
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, "/chat/message/sendMessage", JsonUtils.fromJson(JsonUtils.toJson(req),Map.class), activity, null);
        edSendMsg.setText("");
    }

}
