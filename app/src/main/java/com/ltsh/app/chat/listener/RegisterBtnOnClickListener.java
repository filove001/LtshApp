package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.user.RegisterUserReq;
import com.ltsh.app.chat.handler.impl.DefaultCallbackHandler;
import com.ltsh.app.chat.service.UserService;
import com.ltsh.app.chat.utils.ServiceContextUtils;

/**
 * Created by Random on 2017/10/26.
 */

public class RegisterBtnOnClickListener implements View.OnClickListener {

    private Activity activity;
    public RegisterBtnOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        final String loginName = ((EditText)activity.findViewById(R.id.reg_edit_login_name)).getText().toString();
        final String password = ((EditText) activity.findViewById(R.id.reg_edit_password)).getText().toString();
        final String nickName = ((EditText) activity.findViewById(R.id.reg_edit_nick_name)).getText().toString();
        RegisterUserReq req = new RegisterUserReq();
        req.setLoginName(loginName);
        req.setPassword(password);
        req.setNickName(nickName);
        UserService userService = ServiceContextUtils.getService(UserService.class);
        userService.register(req, new DefaultCallbackHandler(){
            @Override
            public void succeed(Result result) {

                activity.finish();
            }
        });
    }

}
