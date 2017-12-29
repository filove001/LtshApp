package com.ltsh.app.chat.entity;


import java.util.Date;

/**
 * Created by fengjianbo on 2017-12-29 11:34:19.
 */

public class UserGroup extends BaseEntity {
    
    /**
     * 名称
     **/
    private String name;
        
    /**
     * 类型
     **/
    private Integer type;
        
    /**
     * 状态
     **/
    private String status;
        
    /**
     * 所有者
     **/
    private Integer owner;
        
    /**
     * 有效期
     **/
    private java.util.Date validity;
        
    /**
     * 级别类型
     **/
    private Integer levelType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }
}
