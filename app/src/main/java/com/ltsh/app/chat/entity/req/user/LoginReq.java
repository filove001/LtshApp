package com.ltsh.app.chat.entity.req.user;

import com.ltsh.app.chat.entity.req.BaseReq;

/**
 * Created by Random on 2018/1/31.
 */

public class LoginReq extends BaseReq {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码随机数
     */
    private String passwordRandomKey;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRandomKey() {
        return passwordRandomKey;
    }

    public void setPasswordRandomKey(String passwordRandomKey) {
        this.passwordRandomKey = passwordRandomKey;
    }
}
