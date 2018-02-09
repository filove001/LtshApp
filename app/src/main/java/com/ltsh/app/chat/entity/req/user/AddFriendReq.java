package com.ltsh.app.chat.entity.req.user;

import com.ltsh.app.chat.entity.req.BaseReq;

/**
 * Created by Random on 2018/1/31.
 */

public class AddFriendReq extends BaseReq {
    /**
     * 好友备注名称
     */
    private String name;
    /**
     * 好友登录名
     */
    private String loginName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
