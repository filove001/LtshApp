package com.ltsh.app.chat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
//                try {
//                    LoginActivity.this.finish();
//                } catch (Throwable throwable) {
//                    LogUtils.error(throwable.getMessage(), throwable);
//                }
                getMainActivity().startActivity(intent);
            }
        });
        final EditText editLoginName = (EditText)findViewById(R.id.login_edit_login_name);
        final EditText editPassword = (EditText)findViewById(R.id.login_edit_password);
        editLoginName.addTextChangedListener(new TextWatcher() {
            private boolean isChange = false;
            private int start = 0;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().endsWith("\n")) {
                    isChange = true;
                    this.start = start;
                    editLoginName.clearFocus();//失去焦点
                    editPassword.requestFocus();//获取焦点
                    s.subSequence(0, s.toString().lastIndexOf("\n"));

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
//                LogUtils.info("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isChange) {
                    isChange = false;
                    String s1 = s.toString();
                    s1 = s1.replaceAll("\n", "");
                    s.clear();
                    s.append(s1);
                }
//                LogUtils.info("");
            }
        });
        editLoginName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    editLoginName.clearFocus();//失去焦点
                    editPassword.requestFocus();//获取焦点
                }
                return false;
            }
        });

    }

}
