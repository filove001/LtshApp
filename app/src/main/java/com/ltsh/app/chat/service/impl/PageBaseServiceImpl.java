package com.ltsh.app.chat.service.impl;

import android.content.Context;

import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.PageReq;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;


/**
 * Created by Random on 2018/1/29.
 */

public abstract class PageBaseServiceImpl extends BaseServiceImpl {



    public PageBaseServiceImpl(Context context) {
        super(context);
    }
    protected abstract String getPageUrl();

    public Result page(PageReq req, CallbackHandler callbackHandler) {
        super.enctyRequest(getPageUrl(), req, callbackHandler);
        return new Result<>(ResultCodeEnum.SUCCESS);
    }
}
