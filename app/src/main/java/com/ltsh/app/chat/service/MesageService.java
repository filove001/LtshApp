package com.ltsh.app.chat.service;

import com.ltsh.app.chat.entity.common.Result;

/**
 * Created by Random on 2018/1/18.
 */

public interface MesageService {
    /**
     * 注册
     * @param account
     * @param nickName
     * @param email
     * @param password
     * @return
     */
    public Result register(String account, String nickName, String email, String password);

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    public Result login(String account, String password);

}
