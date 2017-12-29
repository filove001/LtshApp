package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.widget.EditText;


import com.ltsh.app.chat.CallbackInterface;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.activity.ContextActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.utils.http.AppHttpClient;


import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.security.MD5Util;

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
        EditText editLoginName = (EditText)activity.findViewById(R.id.login_edit_login_name);
        final String loginName = editLoginName.getText().toString();
        EditText editPassword = (EditText)activity.findViewById(R.id.login_edit_password);
        final String password = editPassword.getText().toString();

        final Map map = new HashMap();
        LogUtils.info("loginName:"+loginName.toString() + "," + "password:"+password.toString());
        map.put("loginName", loginName.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<String[]> random = AppHttpClient.getRandom(AppConstants.SERVLCE_URL);
                if(random.getCode().equals("000000")) {
                    String[] content = random.getContent();
                    map.put("password", MD5Util.encoder("ltshChat:" + MD5Util.encoder("chat:"+MD5Util.encoder("ltshUser:" + password.toString())) + content[1]));
                    map.put("passwordRandomStr", content[0]);
                }
                AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.LOGIN_URL, map, activity, new CallbackInterface() {
                    @Override
                    public void callBack(Result result) {
                        if(ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
                            Map resultMap = (Map)result.getContent();
                            CacheObject.userToken = JsonUtils.fromJson(JsonUtils.toJson(resultMap), UserToken.class);
                            BaseDao.delete(UserToken.class, null, null);
                            BaseDao.insert(CacheObject.userToken);
                            Intent intent = new Intent("android.intent.action.CONTEXT");
                            intent.setClassName(activity, ContextActivity.class.getName());
                            activity.startActivity(intent);
                        }
                    }
                });
            }
        }).start();

    }

}
