package com.ltsh.app.chat.utils;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ltsh.app.chat.CallBackInterface;
import com.ltsh.app.chat.activity.LoginActivity;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/10/11.
 */

public class AppHttpClient {
    public static String post(String baseUrl,String url, Map<String, Object> json, String randomValue) {
        json.putAll(AppInit.commonParams);
        json.put("timestamp", Calendar.getInstance().getTimeInMillis() + "");
        json.put("keep", StringUtils.getUUID());
        if(CacheObject.userToken != null && CacheObject.userToken.getToken() != null) {
            json.put("token", CacheObject.userToken.getToken());
        }
        String signStr = SignUtils.getSignStr(json);
        LogUtils.i("签名明文:" + signStr);
        String sign = SignUtils.getSign(signStr, "123456", randomValue);
        LogUtils.i("签名:" + sign +", randomValue:" + randomValue);
        json.put("signInfo", sign);
        LogUtils.i(String.format("请求连接:%s,请求参数:%s",baseUrl + url, json));
        return OkHttpUtils.post(baseUrl + url, json);
    }
    public static String post(String baseUrl,String url, Map<String, Object> json) {
        return post(baseUrl, url, json, "");
    }
    public static void threadPost(final String baseUrl, final String url, final Map<String, Object> json, final CallBackInterface callBackInterface, final Context activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<Map> mapResult = appPost(baseUrl, url, json);

                if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
                    try {
                        callBackInterface.callBack(mapResult);
                    } catch (Exception e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                } else if(ResultCodeEnum.TOKEN_FAIL.getCode().equals(mapResult.getCode())) {
//                    CacheObject.handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent loginIntent = new Intent("android.intent.action.LOGIN");
//                            loginIntent.setClassName(activity, LoginActivity.class.getName());
//                            activity.startActivity(loginIntent);
//                            if(activity instanceof Activity) {
//                                Activity activity1= (Activity)activity;
//                                activity1.finish();
//                            }
//                        }
//                    });
                } else {
                    CacheObject.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "默认Toast样式", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public static void threadPost(final String baseUrl, final String url, final BaseEntity json, final CallBackInterface callBackInterface, final Context activity) {
        Map map = JsonUtils.fromJson(JsonUtils.toJson(json), Map.class);
        threadPost(baseUrl, url, map, callBackInterface, activity);

    }
    public static Result<Map> appPost(String baseUrl, String url, Map<String, Object> json){
        Result<String[]> randomResult = getRandom(baseUrl);
        if(randomResult.getCode().equals("000000")) {
            String[] random = randomResult.getContent();
            json.put("randomKey", random[0]);

            String post = post(baseUrl, url, json, random[1]);
            LogUtils.i("返回参数:" + post);
            Map map = JsonUtils.fromJson(post, Map.class);
            if(ResultCodeEnum.SUCCESS.getCode().equals(map.get("code"))) {
                return new Result<>((String)map.get("code"), (String)map.get("message"), (Map)map.get("content"));
            } else {
                return new Result<>((String)map.get("code"), (String)map.get("message"));
            }
        } else {
            return new Result<>(randomResult.getCode(), randomResult.getMessage());
        }
    }
    public static Result<String[]> getRandom(String baseUrl) {
        String url = AppConstants.GET_RANDOM_URL;
        Map<String, Object> params = new HashMap<>();
        params.putAll(AppInit.commonParams);
        String uuid = StringUtils.getUUID();
        params.put("uuid", uuid);
        String post = post(baseUrl, url, params);
        LogUtils.i("返回参数:" + post);
        Map map = JsonUtils.fromJson(post, Map.class);
        if(!map.get("code").equals("000000")) {
            return new Result<>((String)map.get("code"), (String)map.get("message"));
        }
        Map content = (Map)map.get("content");
        String randomKey = (String)content.get("randomKey");
        String randomValue = (String) content.get("randomValue");
        String[] str = new String[2];
        try {
            str[0] = AES.decrypt(randomKey, uuid);
            str[1] = AES.decrypt(randomValue, uuid);
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
        }
        return new Result<>(str);
    }
}
