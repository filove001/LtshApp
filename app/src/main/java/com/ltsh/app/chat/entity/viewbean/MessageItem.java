package com.ltsh.app.chat.entity.viewbean;

import com.ltsh.app.chat.entity.BaseEntity;

import java.util.Date;

/**
 * Created by Random on 2017/11/3.
 */

public class MessageItem extends BaseEntity {
    /**
     * 创建者名称(对于消息是发送者)
     */
    private String name;
    /**
     * 发送中数量
     */
    private Integer fszCount;
    /**
     * 未读数量
     */
    private Integer wdCount;
    /**
     * 已读信息数
     */
    private Integer ydCount;
    /**
     * 最后发送信息的时间
     */
    private String lastTime;
    /**
     * 最后发送信息的内容
     */
    private String lastMsg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFszCount() {
        return fszCount;
    }

    public void setFszCount(Integer fszCount) {
        this.fszCount = fszCount;
    }

    public Integer getWdCount() {
        return wdCount;
    }

    public void setWdCount(Integer wdCount) {
        this.wdCount = wdCount;
    }

    public Integer getYdCount() {
        return ydCount;
    }

    public void setYdCount(Integer ydCount) {
        this.ydCount = ydCount;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
