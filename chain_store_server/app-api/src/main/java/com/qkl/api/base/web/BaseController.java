package com.qkl.api.base.web;

import com.qkl.api.user.dto.VerifyCodeRequest;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.sms.cache.SmsCodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller 基类
 * Created by dengjihai on 2018/3/5.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 校验手机验证码
     * @param verifyCodeRequest 验证信息
     * @param smsCodeCache 缓存管理类
     * @return
     */
    public JsonResponse validateVerificationCode(VerifyCodeRequest verifyCodeRequest, SmsCodeCache smsCodeCache){

        if (null==verifyCodeRequest || StringUtil.isBlank(verifyCodeRequest.getMobile())){
            return WebUtil.errorJsonResponse("手机号码不能为空");
        }

        // 校验实际验证码
        if (smsCodeCache.codeIsValid(verifyCodeRequest.getMobile(), verifyCodeRequest.getCode())) {
            logger.info("\n "+verifyCodeRequest.getMobile()+"的验证码校验通过！");
            return WebUtil.successJsonResponse("校验成功");
        }

        logger.info("\n 抱歉，"+verifyCodeRequest.getMobile()+"的验证码校验不通过！");
        return WebUtil.errorJsonResponse("验证码不正确");
    }

}
