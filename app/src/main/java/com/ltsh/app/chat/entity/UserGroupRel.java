package com.ltsh.app.chat.entity;


/**
 * Created by fengjianbo on 2017-12-29 11:34:19.
 */

public class UserGroupRel extends BaseEntity {
    
    /**
     * 昵称
     **/
    private String nickName;
        
    /**
     * 角色
     **/
    private Integer role;
        
    /**
     * 级别
     **/
    private String level;
        
    /**
     * 群组id
     **/
    private Integer groupId;
        
    /**
     * 用户id
     **/
    private Integer userId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
