package com.ltsh.app.chat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.user.AddFriendReq;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.handler.impl.DefaultCallbackHandler;
import com.ltsh.app.chat.service.UserFriendService;
import com.ltsh.app.chat.utils.ServiceContextUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class AddFriendActivity extends BaseActivity {


    private EditText friend_account;
    private EditText friend_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friend_add);
        friend_account = findViewById(R.id.friend_account);
        friend_name = findViewById(R.id.friend_name);
        Button backBtn = (Button)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friendName = friend_name.getText().toString();
                String friendAccount = friend_account.getText().toString();
                AddFriendReq req = new AddFriendReq();
                req.setName(friendName);
                req.setLoginName(friendAccount);
                UserFriendService userFriendService = ServiceContextUtils.getService(UserFriendService.class);
                userFriendService.addFriend(req, new DefaultCallbackHandler(){
                    @Override
                    public void succeed(Result result) {
                        AddFriendActivity.this.finish();
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }


}
