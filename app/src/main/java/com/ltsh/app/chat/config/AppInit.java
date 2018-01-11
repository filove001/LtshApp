package com.ltsh.app.chat.config;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.ltsh.app.chat.utils.db.MyDBOpenHelper;

/**
 * Created by Random on 2017/9/28.
 */

public class AppInit {

    public static void init(Context context) {
        if (CacheObject.dbHelper == null) {
            CacheObject.dbHelper = new MyDBOpenHelper(context, "ltsh-app.db", null, 1);
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = "0000000000";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            imei = telephonyManager.getDeviceId();
        }
        CacheObject.commonParams.put("appVersion", android.os.Build.VERSION.RELEASE);
        CacheObject.commonParams.put("medium", imei);
        CacheObject.commonParams.put("appId", "app");
        CacheObject.commonParams.put("mediumType", "0");
    }
}
