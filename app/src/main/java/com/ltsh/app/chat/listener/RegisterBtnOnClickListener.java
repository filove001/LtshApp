package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.req.RegisterUserReq;
import com.ltsh.app.chat.utils.http.AppHttpClient;

import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.security.MD5Util;

import java.util.Map;

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
        req.setPassword(MD5Util.encoder("ltshUser:" + password));
        req.setNickName(nickName);
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.REGISTER_URL, JsonUtils.fromJson(JsonUtils.toJson(req),Map.class), activity, null);
    }

}
