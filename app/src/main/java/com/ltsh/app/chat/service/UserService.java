package com.ltsh.app.chat.service;

import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.user.RegisterUserReq;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.entity.req.user.LoginReq;

/**
 * Created by Random on 2018/1/18.
 */

public interface UserService extends BaseService {
    /**
     * 登录
     */
    public Result login(LoginReq req, CallbackHandler callbackHandler);

    /**
     * 注册
     * @param req
     * @param callbackHandler
     * @return
     */
    public Result register(RegisterUserReq req, CallbackHandler callbackHandler);
}
