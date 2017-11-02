package com.ltsh.app.chat.activity;

import android.os.Bundle;
import android.widget.Button;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.listener.LoginOnClickListener;

/**
 * Created by Random on 2017/10/12.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new LoginOnClickListener(this));

    }

}
