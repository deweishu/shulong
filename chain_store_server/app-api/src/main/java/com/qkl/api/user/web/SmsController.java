package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.dto.SendCodeRequest;
import com.qkl.api.user.dto.VerifyCodeRequest;
import com.qkl.common.dto.AppVoid;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.AppException;
import com.qkl.common.web.JsonResponse;
import com.qkl.common.web.StandardResponseHeader;
import com.qkl.msg.bean.SmsTemplate;
import com.qkl.msg.service.SmsRecordService;
import com.qkl.sms.cache.SmsCodeCache;
import com.qkl.sms.dto.SendSmsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 短信验证码
 * Created by Benson on 2018/3/2.
 */
@Api(description = "短信验证码")
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Autowired
    private SmsCodeCache smsCodeCache;
    @Autowired
    private SmsRecordService smsRecordService;


    /**
     * 发送验证码请求
     * @param sendCodeRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="发送验证码", notes="发送验证码，手机号必填。", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/code")
    public JsonResponse sendCode(@Valid @RequestBody SendCodeRequest sendCodeRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        // 读取短信模板
        SmsTemplate template = SmsTemplate.getTemplateByCode(sendCodeRequest.getModuleType());
        SendSmsResult sendSmsResult = smsRecordService.sendSmsCode(sendCodeRequest.getMobile(), template);
        if (sendSmsResult.isSuccess()) {
            return WebUtil.successJsonResponse("发送短信成功");
        } else {
            return WebUtil.errorJsonResponse(sendSmsResult.getMsg());
        }
    }


    /**
     * 校验验证码
     * @param verifyCodeRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="校验验证码", notes="校验验证码，手机号和验证码为必填。", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/code/validate")
    public AppVoid validateCode(@Valid @RequestBody VerifyCodeRequest verifyCodeRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        // 短信验证码校验
        JsonResponse errorResponse = validateVerificationCode(verifyCodeRequest, smsCodeCache);
        if (!StandardResponseHeader.SUCCESS.getCode().equals(errorResponse.getCode())){
            throw new AppException(errorResponse.getMsg());
        }

        // 校验通过
        return AppVoid.getInstance();
    }
}
