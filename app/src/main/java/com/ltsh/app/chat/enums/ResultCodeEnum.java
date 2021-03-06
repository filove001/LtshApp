package com.ltsh.app.chat.enums;

/**
 * Created by Random on 2017/9/27.
 */

public enum ResultCodeEnum {

    SUCCESS("000000", "成功"),
    REQUEST_ERROR("999998", "服务器连接失败"),
    LOGIN_FAIL("999997", "登录失败"),
    REGISTER_FAIL("999997", "注册失败"),
    TOKEN_FAIL("990004", "token已失效"),
    SYSTEM_ERROR("999999", "系统繁忙,请稍后再试!"),
    ;

    private String code;
    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
