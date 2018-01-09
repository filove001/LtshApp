package com.ltsh.app.chat;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;

import com.ltsh.app.chat.activity.BaseActivity;
import com.ltsh.app.chat.activity.ContextActivity;
import com.ltsh.app.chat.activity.LoginActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.config.AppInit;

import com.ltsh.common.util.LogUtils;

import java.util.List;


/**
 * Created by Coder-pig on 2015/8/30 0030.
 */
public class MainActivity extends BaseActivity {
    Intent loginIntent;
    Intent contextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInit.init(this);
        CacheObject.handler = new Handler();
        List<UserToken> list = BaseDao.query(UserToken.class, null, null, null);
        if(list != null && !list.isEmpty()) {
            CacheObject.userToken = list.get(0);
            contextIntent = new Intent("android.intent.action.CONTEXT");
            contextIntent.setClassName(this, ContextActivity.class.getName());
            startActivity(contextIntent);
        } else {
            loginIntent = new Intent("android.intent.action.LOGIN");
            loginIntent.setClassName(this, LoginActivity.class.getName());
            startActivity(loginIntent);
        }


//        this.finish();
    }
    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtils.info("按下了back键   onBackPressed()");

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        LogUtils.info("执行 onDestroy()");
    }
}
