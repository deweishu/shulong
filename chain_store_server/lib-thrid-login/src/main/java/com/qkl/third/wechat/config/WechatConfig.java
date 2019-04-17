package com.qkl.third.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  微信登录，分享使用
 * @author dengjihai
 * @create 2018-09-18
 **/
@Component
@ConfigurationProperties(prefix="spring.wechat")
public class WechatConfig  {

    private String appId;

    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
