package com.ltsh.app.chat.service.impl;

import android.content.Context;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.user.RegisterUserReq;
import com.ltsh.app.chat.entity.resp.RandomResp;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.entity.req.user.LoginReq;
import com.ltsh.app.chat.service.BaseService;
import com.ltsh.app.chat.service.BasicsService;
import com.ltsh.app.chat.service.UserService;
import com.ltsh.app.chat.utils.ServiceContextUtils;
import com.ltsh.app.chat.utils.security.PasswordUtils;
import com.ltsh.common.util.security.MD5Util;

/**
 * Created by Random on 2018/1/31.
 */

public class UserServiceImpl extends BaseServiceImpl implements UserService {
    public UserServiceImpl(Context context) {
        super(context);
    }

    @Override
    public Result login(final LoginReq req, final CallbackHandler callbackHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BasicsService basicsService = ServiceContextUtils.getService(BasicsService.class);
                Result<RandomResp> randomResult = basicsService.getRandom();
                if(randomResult.isSuccess()) {
                    RandomResp randomResp = randomResult.getContent();
                    String password = req.getPassword();
                    req.setPassword(PasswordUtils.createLoginPassword(password, randomResp.getRandomValue()));
                    req.setPasswordRandomKey(randomResp.getRandomKey());
                    UserServiceImpl.this.enctyRequest(AppConstants.LOGIN_URL, req, callbackHandler);
                }

            }
        }).start();
        return new Result(ResultCodeEnum.SUCCESS);
    }


    /**
     * 注册
     * @param req
     * @param callbackHandler
     * @return
     */
    public Result register(RegisterUserReq req, CallbackHandler callbackHandler) {
        req.setPassword(PasswordUtils.createPassword(req.getPassword()));
        super.enctyRequest(AppConstants.REGISTER_URL, req, callbackHandler);
        return new Result(ResultCodeEnum.SUCCESS);
    }
}
