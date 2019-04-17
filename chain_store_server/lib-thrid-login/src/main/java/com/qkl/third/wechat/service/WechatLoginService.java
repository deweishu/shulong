package com.qkl.third.wechat.service;

import com.qkl.common.util.JsonUtil;
import com.qkl.third.wechat.config.WechatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

/**
 * 微信移动端登录处理
 * @author dengjihai
 * @create 2018-09-18
 **/
@Service
public class WechatLoginService  {

    private static final Logger logger = LoggerFactory.getLogger(WechatLoginService.class);


    @Autowired
    WechatConfig wechatConfig;



    public String wechatLogin(String code){
        SnsToken token = SnsAPI.oauth2AccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret(), code);
        BaseResult baseResult = SnsAPI.auth(token.getAccess_token(), token.getOpenid());
        logger.info("\n ### 成功获得信信登录用户 user info {},:", JsonUtil.beanToJson(baseResult));
        if("0".equals(baseResult.getErrcode()) && "ok".equals(baseResult.getErrmsg())){
            User user = SnsAPI.userinfo(token.getAccess_token(), token.getOpenid(), "zh-CN");

        }
        return null;
    }

}
