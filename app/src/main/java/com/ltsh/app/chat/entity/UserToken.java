package com.ltsh.app.chat.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Random on 2017/10/11.
 */

public class UserToken extends BaseEntity {
    private String loginName;//登录名
    private String name;//显示名
    private String phone;//手机号码
    private String token;
    private Date loginDate;//登录时间
    public UserToken(){}
    public UserToken(Integer id, String loginName, String name, String phone, String token, Date loginDate) {
        setId(id);
        this.loginName = loginName;
        this.name = name;
        this.phone = phone;
        this.token = token;
        this.loginDate = loginDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
