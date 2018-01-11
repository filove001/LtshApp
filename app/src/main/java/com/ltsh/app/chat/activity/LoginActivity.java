package com.ltsh.app.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.listener.LoginOnClickListener;
import com.ltsh.common.util.LogUtils;

/**
 * Created by Random on 2017/10/12.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = (Button)findViewById(R.id.submit_btn);
        loginBtn.setOnClickListener(new LoginOnClickListener(this));
        Button regBtn = (Button)findViewById(R.id.reg_btn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.REGISTER");
                intent.setClassName(LoginActivity.this, RegisterActivity.class.getName());
                try {
                    LoginActivity.this.finish();
                } catch (Throwable throwable) {
                    LogUtils.error(throwable.getMessage(), throwable);
                }
                getMainActivity().startActivity(intent);
            }
        });
    }

}
