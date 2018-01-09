package com.ltsh.app.chat.entity;


import java.util.Date;

/**
 * 消息记录
 * Created by Random on 2017/10/23.
 */

public class MessageInfo extends BaseEntity {

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
     * 发送人
     */
    private Integer sendUser;
    /**
     * 接受人
     */
    private Integer toUser;

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
     * 来源id
     */
    private String sourceId;
    /**
     * 来源类型
     */
    private String sourceType;



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

    public Integer getSendUser() {
        return sendUser;
    }

    public void setSendUser(Integer sendUser) {
        this.sendUser = sendUser;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
