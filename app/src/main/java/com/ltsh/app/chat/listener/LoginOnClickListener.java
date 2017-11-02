package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.widget.EditText;

import com.ltsh.app.chat.CallBackInterface;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.activity.ContextActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.utils.AppHttpClient;
import com.ltsh.app.chat.utils.JsonUtils;
import com.ltsh.app.chat.utils.LogUtils;
import com.ltsh.app.chat.utils.MD5Util;

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
        EditText editLoginName = (EditText)activity.findViewById(R.id.editLoginName);
        final String loginName = editLoginName.getText().toString();
        EditText editPassword = (EditText)activity.findViewById(R.id.editPassword);
        final String password = editPassword.getText().toString();

        final Map map = new HashMap();
        LogUtils.i("loginName:"+loginName.toString() + "," + "password:"+password.toString());
        map.put("loginName", loginName.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<String[]> random = AppHttpClient.getRandom(AppConstants.SERVLCE_URL);
                if(random.getCode().equals("000000")) {
                    String[] content = random.getContent();
                    map.put("password", MD5Util.encoder("ltshChat:" + MD5Util.encoder("chat:"+password.toString()) + content[1]));
                    map.put("passwordRandomStr", content[0]);
                }
                Result<Map> result = AppHttpClient.appPost(AppConstants.SERVLCE_URL, AppConstants.LOGIN_URL, map);
                if(ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
                    Map resultMap = result.getContent();
                    CacheObject.userToken = JsonUtils.fromJson(JsonUtils.toJson(resultMap), UserToken.class);
                    DbUtils.dbHelper.getWritableDatabase().delete(DbUtils.getTableName(UserToken.class), null, null);
                    DbUtils.insert(CacheObject.userToken);
                    Intent intent = new Intent("android.intent.action.CONTEXT");
                    intent.setClassName(activity, ContextActivity.class.getName());
                    activity.startActivity(intent);
                }
            }
        }).start();

    }

}