package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.view.View;
import android.widget.EditText;


import com.ltsh.app.chat.R;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.handler.impl.DefaultCallbackHandler;
import com.ltsh.app.chat.entity.req.user.LoginReq;
import com.ltsh.app.chat.service.UserService;
import com.ltsh.app.chat.ui.activity.ContextActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.utils.ServiceContextUtils;


import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/10/13.
 */

public class LoginOnClickListener implements View.OnClickListener {
    private Activity activity;

    public LoginOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        final EditText editLoginName = (EditText)activity.findViewById(R.id.login_edit_login_name);

        final String loginName = editLoginName.getText().toString();
        final EditText editPassword = (EditText)activity.findViewById(R.id.login_edit_password);
        final String password = editPassword.getText().toString();
        final Map map = new HashMap();
        LogUtils.info("loginName:"+loginName.toString() + "," + "password:"+password.toString());
        map.put("loginName", loginName.toString());
        UserService userService = ServiceContextUtils.getService(UserService.class);
        LoginReq req = new LoginReq();
        req.setLoginName(loginName);
        req.setPassword(password);



        userService.login(req, new DefaultCallbackHandler() {
            ProgressDialog progressDialog = null;
            @Override
            public void before(AppReq appReq) {
                CacheObject.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(activity, "资源加载中", "资源加载中,请稍后...", false, false);
                    }
                });

            }

            @Override
            public void succeed(Result result) {
                if (ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
                    Map resultMap = (Map) result.getContent();
                    CacheObject.userToken = JsonUtils.fromJson(JsonUtils.toJson(resultMap), UserToken.class);
                    BaseDao.delete(UserToken.class, null, null);
                    BaseDao.insert(CacheObject.userToken);
                    Intent intent = new Intent("android.intent.action.CONTEXT");
                    intent.setClassName(activity, ContextActivity.class.getName());
                    activity.startActivity(intent);
                    activity.finish();
                }
            }

            @Override
            public void complete(Result result) {
                if(progressDialog != null) {
                    progressDialog.cancel();
                }
            }
        });

    }

}
