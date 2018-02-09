package com.ltsh.app.chat.service.impl;

import android.content.Context;
import android.widget.Toast;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.entity.req.BaseReq;
import com.ltsh.app.chat.entity.req.RandomReq;
import com.ltsh.app.chat.entity.resp.RandomResp;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.service.BaseService;
import com.ltsh.app.chat.service.BasicsService;
import com.ltsh.app.chat.utils.ServiceContextUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.StringUtils;

import org.slf4j.MDC;


/**
 * Created by Random on 2018/1/29.
 */

public class BaseServiceImpl implements BaseService {

    private Context context;

    public BaseServiceImpl(Context context) {
        this.context = context;
    }

    public void enctyRequest(final String url, final AppReq req, final CallbackHandler callBackHandler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BasicsService basicsService = ServiceContextUtils.getService(BasicsService.class);
                Result<RandomResp> randomResult = basicsService.getRandom();
                if(randomResult.isSuccess()) {
                    serviceRequest(AppConstants.SERVLCE_URL, url, req, randomResult.getContent(), callBackHandler);
                } else {
                    LogUtils.error(randomResult.getCode() + ":" + randomResult.getMessage());
                }
            }
        });
        thread.start();
    }
    public void enctyRequest(final String url, final BaseEntity baseEntity, final CallbackHandler callBackHandler) {
        AppReq<BaseEntity> appReq = new AppReq<>(baseEntity);
        enctyRequest(url, appReq, callBackHandler);
    }
    public void enctyRequest(final String url, final BaseReq req, final CallbackHandler callBackHandler) {
        AppReq<BaseReq> appReq = new AppReq<>(req);
        enctyRequest(url, appReq, callBackHandler);
    }
    public void enctyRequest(final String url, final CallbackHandler callBackHandler) {
        AppReq<BaseReq> appReq = new AppReq<>(null);
        enctyRequest(url, appReq, callBackHandler);
    }
    public void serviceRequest(final String baseUrl, final String url, final AppReq req, final RandomResp resp, final CallbackHandler callBackHandler) {
        MDC.put("keep", StringUtils.getUUID());
        if(callBackHandler != null) {
            callBackHandler.before(req);
        }
        String result = AppHttpClient.postJson(baseUrl, url, req, resp);
        final Result mapResult = JsonUtils.fromJson(result, Result.class);

        if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
            if(callBackHandler != null) {
                callBackHandler.succeed(mapResult);
            }
        } else {
            if(callBackHandler != null) {
                callBackHandler.fail(mapResult);
            } else {
                if(CacheObject.handler!= null) {
                    CacheObject.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mapResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
        if(callBackHandler != null) {
            callBackHandler.complete(mapResult);
        }
        MDC.remove("keep");

    }
}
