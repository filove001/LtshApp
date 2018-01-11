package com.ltsh.app.chat;


import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;

import com.ltsh.app.chat.activity.BaseActivity;
import com.ltsh.app.chat.activity.ContextActivity;
import com.ltsh.app.chat.activity.LoginActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.config.AppInit;

import com.ltsh.app.chat.receiver.NetworkStateReceiver;
import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Coder-pig on 2015/8/30 0030.
 */
public class MainActivity extends BaseActivity {
    Intent loginIntent;
    Intent contextIntent;
    NetworkStateReceiver networkStateReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isAllPermission = true;
        int MY_PERMISSIONS_REQUEST_CALL_PHONE = 123;
        List<String> allPermission = new ArrayList<>();


        allPermission.add(Manifest.permission.INTERNET);
        allPermission.add(Manifest.permission.READ_PHONE_STATE);
        allPermission.add(Manifest.permission.ACCESS_NETWORK_STATE);
        allPermission.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
        allPermission.add(Manifest.permission.WRITE_SETTINGS);
        allPermission.add(Manifest.permission.VIBRATE);
        allPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        allPermission.add(Manifest.permission.DISABLE_KEYGUARD);
        allPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        allPermission.add(Manifest.permission.ACCESS_WIFI_STATE);
        allPermission.add(Manifest.permission.RESTART_PACKAGES);
        allPermission.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
        allPermission.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
        allPermission.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        allPermission.add(Manifest.permission.RECORD_AUDIO);
        allPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        allPermission.add(Manifest.permission.CAPTURE_AUDIO_OUTPUT);
        allPermission.add(Manifest.permission.CAPTURE_VIDEO_OUTPUT);
        for (String key :
                allPermission) {
            int permission = ContextCompat.checkSelfPermission(this, key);
            if(permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        allPermission.toArray(new String[allPermission.size()]),
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                isAllPermission = false;
            }
        }
        if(isAllPermission) {
            start();
        }

//        this.finish();
    }
    private void start() {
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
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        networkStateReceiver = new NetworkStateReceiver();
        registerReceiver(networkStateReceiver,filter);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //调用自己封装的方法 处理授权结果
        LogUtils.info("requestCode:{}, {}, {}", requestCode, JsonUtils.toJson(permissions), JsonUtils.toJson(grantResults));
        if(permissions.length > 0) {
            start();
        }

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
        unregisterReceiver(networkStateReceiver);
        LogUtils.info("执行 onDestroy()");
    }
}
