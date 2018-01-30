package com.ltsh.app.chat.config;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.ltsh.app.chat.db.DBCipherHelper;
import com.ltsh.app.chat.entity.req.BaseReq;
import com.ltsh.app.chat.utils.SystemUtil;
import com.ltsh.app.chat.utils.cache.DiskLruCache;
import com.ltsh.common.util.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Random on 2017/9/28.
 */

public class AppInit {
    public static void init(Context context) {
        if (CacheObject.dbHelper == null) {
            CacheObject.dbHelper = new DBCipherHelper(context, "ltsh-app.db", null, 1);
        }
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

//        CacheObject.commonParams.put("appVersion", android.os.Build.VERSION.RELEASE);
//        CacheObject.commonParams.put("medium", imei);
//        CacheObject.commonParams.put("appId", AppConstants.APP_ID);
//        CacheObject.commonParams.put("mediumType", "0");
        String diskCachePath = context.getCacheDir().getPath() + "/CacheDir";
        File diskCachePathFile = new File(diskCachePath);
        try {
            if(CacheObject.diskLruCache == null) {
                CacheObject.diskLruCache = DiskLruCache.open(diskCachePathFile, 1, 1, AppConstants.DISK_MAX_SIZE);
            }
        } catch (IOException e) {
            LogUtils.error(e.getMessage(), e);
        }


    }
}
