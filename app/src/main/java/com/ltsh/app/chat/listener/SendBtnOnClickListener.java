package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.ltsh.app.chat.CallBackInterface;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.utils.AppHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/10/26.
 */

public class SendBtnOnClickListener implements View.OnClickListener, CallBackInterface {
    private Integer toUser;
    private Activity activity;
    public SendBtnOnClickListener(Activity activity, Integer toUser) {
        this.toUser = toUser;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        EditText edSendMsg = (EditText)activity.findViewById(R.id.ed_send_msg_input);
        String sendMessage = edSendMsg.getText().toString();
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsgContext(sendMessage);
        messageInfo.setToUser(toUser);
        messageInfo.setMsgType(0);
        messageInfo.setSendType(0);
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, "/chat/message/sendMessage", messageInfo, this, activity);
    }

    @Override
    public void callBack(Result result) {

    }
}
