package com.ltsh.app.chat.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.adapter.ChatAdapter;
import com.ltsh.app.chat.adapter.MessageAdapter;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.fragment.FriendFragment;
import com.ltsh.app.chat.fragment.ChatListFragment;
import com.ltsh.app.chat.listener.SendBtnOnClickListener;
import com.ltsh.app.chat.utils.JsonUtils;
import com.ltsh.app.chat.utils.LogUtils;

import java.util.Calendar;
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
                final List<MessageInfo> messageInfos = DbUtils.query(MessageInfo.class, "(create_by=? and to_user=?) or (create_by=? and to_user=?)", new String[]{userFriend.getFriendUserId() + "", CacheObject.userToken.getId() + "", CacheObject.userToken.getId() + "", userFriend.getFriendUserId() + ""}, null);
                CacheObject.chatAdapter.addAll(messageInfos);
            }
        });

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