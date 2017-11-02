package com.ltsh.app.chat.entity;

import com.ltsh.app.chat.db.NoDbColumn;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * Created by Random on 2017/9/19.
 */

public class MessageInfo extends BaseEntity {

    /**
     * 创建用户名称
     */
    private String createByName;


    /**
     * 消息内容
     */
    private String msgContext;

    /**
     * 模板id
     */
    private Integer templateId;
    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 接收人
     */
    private Integer toUser;
    /**
     * 接收用户名称
     */
    private String toUserName;
    /**
     * 发送类型
     */
    private Integer sendType;
    /**
     * 发送时间
     */
    private Date sendDate;
    /**
     * 读取时间
     */
    private Date readTime;

    /**
     * 状态
     */
    private String status;
    /**
     * 图片List
     */
    @NoDbColumn
    private List<String> imageList;

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getMsgContext() {
        return msgContext;
    }

    public void setMsgContext(String msgContext) {
        this.msgContext = msgContext;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
