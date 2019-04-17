package com.qkl.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码属性配置
 * Created by dengjihai on 2018/3/28.
 */
@Configuration
public class VerifyCodeConfig {

    @Value("${spring.verify.code.close:false}")
    private boolean close; //是否开启验证码发送功能

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

}
