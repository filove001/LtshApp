package com.ltsh.app.chat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.listener.LoginOnClickListener;
import com.ltsh.app.chat.listener.RegisterBtnOnClickListener;

/**
 * Created by Random on 2017/10/12.
 */

public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button submitBtn = (Button)findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new RegisterBtnOnClickListener(this));
        Button backBtn = (Button)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
