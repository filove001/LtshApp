package com.ltsh.app.chat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.adapter.ChatAdapter;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.dao.MessageItemDao;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.listener.SendBtnOnClickListener;

import com.ltsh.common.util.JsonUtils;

import java.util.List;

/**
 * Created by Random on 2017/10/13.
 */

public class ChatActivity extends BaseActivity {
    private TextView titleView;
    private Button backBtn;
    private Button sendBtn;
    private ListView chat_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindViews();
        String friendData = this.getIntent().getStringExtra("friendData");
        final UserFriend userFriend = JsonUtils.fromJson(friendData, UserFriend.class);
        titleView.setText(userFriend.getName());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendBtn.setOnClickListener(new SendBtnOnClickListener(this, userFriend));
        chat_list = (ListView) findViewById(R.id.chat_list);
        if(CacheObject.chatAdapter == null) {
            CacheObject.chatAdapter = new ChatAdapter(this, chat_list);
        }
        chat_list.setAdapter(CacheObject.chatAdapter);

        CacheObject.handler.post(new Runnable() {
            @Override
            public void run() {
                final List<MessageInfo> messageInfos = BaseDao.query(MessageInfo.class, "(create_by=? and to_user=?) or (create_by=? and to_user=?)", new String[]{userFriend.getFriendUserId() + "", CacheObject.userToken.getId() + "", CacheObject.userToken.getId() + "", userFriend.getFriendUserId() + ""}, null);
                CacheObject.chatAdapter.addAll(messageInfos);
            }
        });
        MessageItemDao.updateMessageRead(CacheObject.userToken.getId(), userFriend.getFriendUserId());
//        chat_list.setOnApplyWindowInsetsListener();
    }

    private void bindViews() {
        titleView = (TextView)findViewById(R.id.txt_title);
        backBtn = (Button)findViewById(R.id.btn_back);
        sendBtn = (Button)findViewById(R.id.btn_send);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}