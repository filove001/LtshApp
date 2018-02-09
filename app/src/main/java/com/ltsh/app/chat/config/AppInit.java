package com.ltsh.app.chat.config;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.ltsh.app.chat.db.DBCipherHelper;

import com.ltsh.app.chat.service.BasicsService;
import com.ltsh.app.chat.service.MessageService;
import com.ltsh.app.chat.service.UserFriendService;
import com.ltsh.app.chat.service.UserGroupRelService;
import com.ltsh.app.chat.service.UserGroupService;
import com.ltsh.app.chat.service.UserService;
import com.ltsh.app.chat.service.impl.BasicsServiceImpl;
import com.ltsh.app.chat.service.impl.MessageServiceImpl;
import com.ltsh.app.chat.service.impl.UserFriendServiceImpl;
import com.ltsh.app.chat.service.impl.UserGroupRelServiceImpl;
import com.ltsh.app.chat.service.impl.UserGroupServiceImpl;
import com.ltsh.app.chat.service.impl.UserServiceImpl;
import com.ltsh.app.chat.utils.ServiceContextUtils;
import com.ltsh.app.chat.utils.SystemUtil;
import com.ltsh.app.chat.utils.cache.DiskLruCache;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Random on 2017/9/28.
 */

public class AppInit {
    /**
     * 设置公用请求参数
     * @param context
     */
    private static void setCommonRequest(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = "0000000000";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            imei = telephonyManager.getDeviceId();
        }
        AppConstants.APP_ID = "app";
        AppConstants.APP_SECRET = "123456";
        CacheObject.baseReq.setAppId(AppConstants.APP_ID);
        CacheObject.baseReq.setAppVersion(SystemUtil.getVerName(context));
        CacheObject.baseReq.setMedium(imei);
        CacheObject.baseReq.setMediumType("1");
        CacheObject.baseReq.setDeviceBrand(SystemUtil.getDeviceBrand());
        CacheObject.baseReq.setSystemLanguage(SystemUtil.getSystemLanguage());
        CacheObject.baseReq.setSystemModel(SystemUtil.getSystemModel());
        CacheObject.baseReq.setSystemVersion(SystemUtil.getSystemVersion());
    }
    private static void initService(Context context) {
        BasicsService basicsService = new BasicsServiceImpl();
        ServiceContextUtils.addService(BasicsService.class,basicsService);
        MessageService messageService = new MessageServiceImpl(context);
        UserService userService = new UserServiceImpl(context);
        UserFriendService userFriendService = new UserFriendServiceImpl(context);
        UserGroupService userGroupService = new UserGroupServiceImpl(context);
        UserGroupRelService userGroupRelService = new UserGroupRelServiceImpl(context);
        ServiceContextUtils.addService(MessageService.class,messageService);
        ServiceContextUtils.addService(UserService.class, userService);
        ServiceContextUtils.addService(UserFriendService.class, userFriendService);
        ServiceContextUtils.addService(UserGroupService.class, userGroupService);
        ServiceContextUtils.addService(UserGroupRelService.class, userGroupRelService);
    }
    public static void init(Context context) {
        if (CacheObject.dbHelper == null) {
            CacheObject.dbHelper = new DBCipherHelper(context, "ltsh-app.db", null, 1);
        }
        AppHttpClient.isDebug = true;
        setCommonRequest(context);
        initService(context);
//        CacheObject.commonParams.put("appVersion", android.os.Build.VERSION.RELEASE);
//        CacheObject.commonParams.put("medium", imei);
//        CacheObject.commonParams.put("appId", AppConstants.APP_ID);
//        CacheObject.commonParams.put("mediumType", "0");
//        String diskCachePath = context.getCacheDir().getPath() + "/CacheDir";
//        File diskCachePathFile = new File(diskCachePath);
//        try {
//            if(CacheObject.diskLruCache == null) {
//                CacheObject.diskLruCache = DiskLruCache.open(diskCachePathFile, 1, 1, AppConstants.DISK_MAX_SIZE);
//            }
//        } catch (IOException e) {
//            LogUtils.error(e.getMessage(), e);
//        }


    }
}
