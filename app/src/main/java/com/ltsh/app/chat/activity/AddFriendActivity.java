package com.ltsh.app.chat.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.service.LoadEntityCallSerivice;
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
        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friendName = friend_name.getText().toString();
                String friendAccount = friend_account.getText().toString();
                Map<String, Object> params = new HashMap<>();
                params.put("name", friendName);
                params.put("loginName", friendAccount);
                AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.ADD_FRIEND_URL, params, null, new CallbackHandler() {
                    @Override
                    public void callBack(Result result) {
                        AddFriendActivity.this.close();
                    }

                    @Override
                    public void error(Result result) {
                        AddFriendActivity.this.close();
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
