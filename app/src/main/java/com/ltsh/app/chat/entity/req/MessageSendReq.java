package com.ltsh.app.chat.entity.req;




/**
 * Created by Random on 2017/07/26.
 */
public class MessageSendReq {
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
     * 接受人
     */
    private Integer toUser;
    /**
     * 接受人姓名
     */
    private String toUserName;
    /**
     * 发送类型
     */
    private Integer sendType;
    /**
     * 发送时间
     */
    private String sendDate;
    /**
     * 短信标志
     */
    private String flag;

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

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
