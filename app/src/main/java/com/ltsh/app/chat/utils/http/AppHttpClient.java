package com.ltsh.app.chat.utils.http;




import android.content.Context;
import android.widget.Toast;

import com.ltsh.app.chat.entity.req.AppReq;

import com.ltsh.app.chat.entity.resp.RandomResp;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.ui.activity.BaseActivity;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;


import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.StringUtils;
import com.ltsh.common.util.http.OkHttpUtils;
import com.ltsh.common.util.security.AES;
import com.ltsh.common.util.security.SignUtils;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.MDC;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Random on 2017/10/11.
 */

public class AppHttpClient {
    public static boolean isDebug = false;
    public static String postJson(String baseUrl, String url, AppReq req, RandomResp randomResp) {
        req.setTimestamp(Calendar.getInstance().getTimeInMillis() + "");
        req.setKeep(StringUtils.getUUID());
        if(randomResp != null) {
            req.setRandomKey(randomResp.getRandomKey());
        }

        if(CacheObject.userToken != null && CacheObject.userToken.getToken() != null) {
            req.setToken(CacheObject.userToken.getToken());
        }
        String signStr = SignUtils.getSignStr(req);
        if(isDebug) {
            LogUtils.info("签名明文:" + signStr);
        }
        String signInfo = null;
        if(randomResp != null) {
            signInfo = SignUtils.getSign(signStr, AppConstants.APP_SECRET, randomResp.getRandomKey());
        } else {
            signInfo = SignUtils.getSign(signStr, AppConstants.APP_SECRET, "");
        }
        req.setSignInfo(signInfo);
        return execute(baseUrl + url, req);
    }

    public static String postJson(String baseUrl,String url, Map<String, Object> json, String randomValue) {
        json.putAll(JsonUtils.fromJson(JsonUtils.toJson(CacheObject.baseReq), Map.class));
        json.put("timestamp", Calendar.getInstance().getTimeInMillis() + "");
        json.put("keep", StringUtils.getUUID());
        if(CacheObject.userToken != null && CacheObject.userToken.getToken() != null) {
            json.put("token", CacheObject.userToken.getToken());
        }
        String signStr = SignUtils.getSignStr(json);
        if(isDebug) {
            LogUtils.info("签名明文:" + signStr);
        }
        String sign = SignUtils.getSign(signStr, AppConstants.APP_SECRET, randomValue);
//        LogUtils.info("签名:" + sign +", randomValue:" + randomValue);
        json.put("signInfo", sign);
        return execute(baseUrl + url, json);
    }
    private static String execute(String url, AppReq req) {
        Map json = JsonUtils.fromJson(JsonUtils.toJson(req), Map.class);
        return OkHttpUtils.postJson(url, json);
    }
    private static String execute(String url, Map<String, Object> json) {
        if(isDebug) {
            LogUtils.info(String.format("请求连接:%s,请求参数:%s", url, JsonUtils.toJson(json)));
        }
        String result = OkHttpUtils.postJson(url, json);
        if(isDebug) {
            LogUtils.info(String.format("返回结果:%s", result));
        }
        return result;
    }
//    public static void threadPost(final String baseUrl, final String url, final Map<String, Object> json, final Context activity, final CallbackHandler callBackHandler) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MDC.put("keep", StringUtils.getUUID());
//
//                final  Result<Map> mapResult = appPost(baseUrl, url, json, activity);
//
//                if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
//                    try {
//                        if(callBackHandler != null) {
//                            callBackHandler.callBack(mapResult);
//                        }
//                    } catch (Exception e) {
//                        LogUtils.error(e.getMessage(), e);
//                    }
//                } else {
//                    if(callBackHandler != null) {
//                        callBackHandler.error(mapResult);
//                    }
//                    if(CacheObject.handler!= null) {
//                        CacheObject.handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity, mapResult.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                }
//
//
//                MDC.remove("keep");
//            }
//        }).start();
//    }

//    public static void threadPost(final String baseUrl, final String url, final BaseEntity json, final Context activity, final CallbackHandler callBackHandler) {
//        Map map = JsonUtils.fromJson(JsonUtils.toJson(json), Map.class);
//        threadPost(baseUrl, url, map, activity, callBackHandler);
//
//    }
//    public static Result<Map> appPost(String baseUrl, String url, Map<String, Object> json, final Context activity) {
//        return appPost(baseUrl,url, json, null, activity);
//    }
//    public static Result<Map> appPost(String baseUrl, String url, AppReq req, RandomResp resp, final Context activity){
//        if(resp == null) {
//            Result<String[]> randomResult = getRandom(baseUrl);
//            if(randomResult.getCode().equals("000000")) {
//                String[] content = randomResult.getContent();
//                resp = new RandomResp(content[0], content[1]);
//            } else {
//                LogUtils.error(randomResult.getCode() + ":" + randomResult.getMessage());
//            }
//
//        }
//
//        if(resp != null) {
//            req.setRandomKey(resp.getRandomKey());
////            json.put("randomKey", resp.getRandomKey());
//            DateTime begin = new DateTime();
//
//            String post = post(baseUrl, url, req, resp.getRandomValue());
//            DateTime end = new DateTime();
//            //计算区间毫秒数
//            Duration d = new Duration(begin, end);
//            LogUtils.info("耗时:"+ d.getMillis() +" ms,返回参数:" + post);
//            Map map = JsonUtils.fromJson(post, Map.class);
//            if(ResultCodeEnum.SUCCESS.getCode().equals(map.get("code"))) {
//                return new Result<>((String)map.get("code"), (String)map.get("message"), (Map)map.get("content"));
//            }else if(ResultCodeEnum.TOKEN_FAIL.getCode().equals((String)map.get("code"))) {
//                if(activity instanceof BaseActivity) {
//                    BaseActivity baseActivity = (BaseActivity) activity;
//                    baseActivity.loginOut();
//                }
//                return new Result<>((String)map.get("code"), (String)map.get("message"));
//            } else {
//                return new Result<>((String)map.get("code"), (String)map.get("message"));
//            }
//        } else {
//            return new Result<>(ResultCodeEnum.SYSTEM_ERROR);
//        }
//    }
//
//    public static Result<String[]> getRandom(String baseUrl) {
//        String url = AppConstants.GET_RANDOM_URL;
//        Map<String, Object> params = new HashMap<>();
//        params.putAll(JsonUtils.fromJson(JsonUtils.toJson(CacheObject.baseReq), Map.class));
//        String uuid = StringUtils.getUUID();
//        params.put("uuid", uuid);
//        String post = post(baseUrl, url, params);
//        LogUtils.info("返回参数:" + post);
//        Map map = JsonUtils.fromJson(post, Map.class);
//        if(!map.get("code").equals("000000")) {
//            return new Result<>((String)map.get("code"), (String)map.get("message"));
//        }
//        Map content = (Map)map.get("content");
//        String randomKey = (String)content.get("randomKey");
//        String randomValue = (String) content.get("randomValue");
//        String[] str = new String[2];
//        try {
//            str[0] = AES.decrypt(randomKey, uuid);
//            str[1] = AES.decrypt(randomValue, uuid);
//        } catch (Exception e) {
//            LogUtils.error(e.getMessage(), e);
//        }
//        return new Result<>(str);
//    }
}
