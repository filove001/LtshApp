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
import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.fragment.FriendFragment;
import com.ltsh.app.chat.fragment.ChatListFragment;
import com.ltsh.app.chat.listener.SendBtnOnClickListener;
import com.ltsh.app.chat.utils.JsonUtils;

/**
 * Created by Random on 2017/10/13.
 */

public class ChatActivity extends BaseActivity {
    private TextView titleView;
    private Button backBtn;
    private Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindViews();
        String friendData = this.getIntent().getStringExtra("friendData");
        UserFriend userFriend = JsonUtils.fromJson(friendData, UserFriend.class);
        titleView.setText(userFriend.getName());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendBtn.setOnClickListener(new SendBtnOnClickListener(this, userFriend.getFriendUserId()));

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