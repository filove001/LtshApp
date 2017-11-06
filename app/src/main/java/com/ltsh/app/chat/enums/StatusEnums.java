package com.ltsh.app.chat.enums;

/**
 * Created by Random on 2017/10/23.
 */
public enum StatusEnums {
    CG("CG", "成功"),
    SC("SC", "删除"),
    DJ("DJ", "冻结"),
    KY("KY", "可用"),
    SX("SX", "失效"),
    WD("WD", "未读"),
    FSZ("FSZ", "发送中"),
    YFS("YFS", "已发送"),
    YD("YD", "已读"),
    SB("SB","失败");


    /** 枚举值 */
    private String value;
    /** 描述 */
    private String desc;
    StatusEnums(String value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
