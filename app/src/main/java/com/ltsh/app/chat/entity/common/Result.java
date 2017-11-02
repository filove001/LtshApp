package com.ltsh.app.chat.entity.common;

import com.ltsh.app.chat.enums.ResultCodeEnum;

/**
 * Created by Random on 2017/9/27.
 */

public class Result<T> {
    private String code;
    private String message;
    private T content;
    public Result() {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
    }
    public Result(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    public Result(String code, String message){
        this.code = code;
        this.message = message;
    }
    public Result(String code, String message, T content){
        this.code = code;
        this.message = message;
        this.content = content;
    }
    public Result(T content){
        this.content = content;
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
    }
    public boolean isSuccess() {
        if("000000".equals(code)) {
            return true;
        }
        return false;
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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
