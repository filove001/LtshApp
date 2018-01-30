package com.ltsh.app.chat.entity.req;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.BeanUtils;

import java.io.Serializable;

/**
 * Created by Random on 2018/1/25.
 */

public class AppReq<T> implements Serializable {
    /**
     * app版本
     */
    private String appVersion;
    /**
     * 设备id
     */
    private String medium;
    /**
     * appId
     */
    private String appId;
    /**
     * 设备类型
     */
    private String mediumType;
    /**
     * 系统版本
     */
    private String systemVersion;
    /**
     * 手机厂商
     */
    private String deviceBrand;
    /**
     * 手机型号
     */
    private String systemModel;
    /**
     * 请求流水
     */
    private String keep;
    /**
     * 系统语言
     */
    private String systemLanguage;
    /**
     * 时间戳字符串
     */
    private String timestamp;
    /**
     * 登录token
     */
    private String token;

    /**
     * 随机数key
     */
    private String randomKey;

    /**
     * 签名值
     */
    private String signInfo;

    private T content;

    public AppReq(AppReq appReq, T content) {
        BeanUtils.copyProperties(appReq, this);
        this.content = content;
    }
    public AppReq() {

    }


    public AppReq(T content) {
        BeanUtils.copyProperties(CacheObject.baseReq, this);
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public void setBaseValue(AppReq baseReq) {
        BeanUtils.copyProperties(baseReq, this);
    }

    public String getSignInfo() {
        return signInfo;
    }

    public void setSignInfo(String signInfo) {
        this.signInfo = signInfo;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMediumType() {
        return mediumType;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getSystemModel() {
        return systemModel;
    }

    public void setSystemModel(String systemModel) {
        this.systemModel = systemModel;
    }

    public String getKeep() {
        return keep;
    }

    public void setKeep(String keep) {
        this.keep = keep;
    }

    public String getSystemLanguage() {
        return systemLanguage;
    }

    public void setSystemLanguage(String systemLanguage) {
        this.systemLanguage = systemLanguage;
    }
}
