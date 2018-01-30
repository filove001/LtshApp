package com.ltsh.app.chat.service.impl;

import android.content.Context;
import android.widget.Toast;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.entity.resp.RandomResp;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.StringUtils;

import org.slf4j.MDC;

import java.util.Map;

/**
 * Created by Random on 2018/1/29.
 */

public class BaseServiceImpl {
    public void threadRequest(final String baseUrl, final String url, final AppReq req, final RandomResp resp, final Context activity) {
        threadRequest(baseUrl, url, req, resp, activity, null);
    }
    public void threadRequest(final String baseUrl, final String url, final AppReq req, final RandomResp resp, final Context activity, final CallbackHandler callBackHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MDC.put("keep", StringUtils.getUUID());

                String result = AppHttpClient.postJson(baseUrl, url, req, resp);
                final Result mapResult = JsonUtils.fromJson(result, Result.class);
                if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
                    try {
                        if(callBackHandler != null) {
                            callBackHandler.callBack(mapResult);
                        }
                    } catch (Exception e) {
                        LogUtils.error(e.getMessage(), e);
                    }
                } else {
                    if(callBackHandler != null) {
                        callBackHandler.error(mapResult);
                    } else {
                        if(CacheObject.handler!= null) {
                            CacheObject.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, mapResult.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                MDC.remove("keep");
            }
        }).start();
    }
}
