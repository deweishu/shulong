package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.api.user.cache.UserTokenCache;
import com.qkl.api.user.dto.SendCodeRequest;
import com.qkl.api.user.dto.UpdateHeadImgReq;
import com.qkl.api.user.dto.UpdateNickReq;
import com.qkl.common.mq.UserMQSender;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.AppException;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import com.qkl.msg.bean.SmsTemplate;
import com.qkl.sms.cache.SmsCodeCache;
import com.qkl.sms.dto.SendSmsResult;
import com.qkl.user.dto.*;
import com.qkl.user.service.SignLogService;
import com.qkl.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author dengjihai
 * @create 2018-08-24
 **/
@Api(description = "用户中心")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;


    @Autowired
    private SmsCodeCache smsCodeCache;

    @Autowired
    TokenSessionCache tokenSessionCache;

    @Autowired
    private UserTokenCache userTokenCache;

    @Autowired
    SignLogService signLogService;


    @ApiOperation(value="根据用户ID获取用户基本信息", notes="根据用户ID获取用户基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/info/{id}")
    public JsonResponse userInfo(@PathVariable String id){
        UserDto userDto=userService.findOne(id);
        Assert.notNull(userDto,"不存在该用户信息");
        userDto.setPassWord(null);
        return WebUtil.successJsonResponse("获取用户信息接口",userDto);
    }

    @ApiOperation(value="修改用户头像接口", notes="修改用户头像接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/update/head/img")
    public JsonResponse updateHeadImg(@Valid @RequestBody UpdateHeadImgReq headImgReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return WebUtil.successJsonResponse("更新用户头像成功",userService.updateHeadImg(tokenSessionCache.getUserId(),headImgReq.getHeadImg()));
    }



    @ApiOperation(value="修改用户昵称接口", notes="修改用户昵称接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/update/nick")
    public JsonResponse updateNickName(@Valid @RequestBody UpdateNickReq updateNickReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return WebUtil.successJsonResponse("修改昵称成功",userService.updateNick(tokenSessionCache.getUserId(),updateNickReq.getNickName()));
    }

    @ApiOperation(value="找回密码接口", notes="找回密码接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/pwd/find")
    public JsonResponse findPwd(@Valid @RequestBody UpdatePwdReq updatePwdReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(StringUtil.isNil(updatePwdReq.getCode())){
            return WebUtil.errorJsonResponse("验证码不能为空");
        }
        if (!smsCodeCache.codeIsValid(updatePwdReq.getPhone(), updatePwdReq.getCode())) {
            return WebUtil.errorJsonResponse("验证码错误");
        }
        String id=userService.restPwd(updatePwdReq);
        return WebUtil.successJsonResponse("找回密码成功");
    }



    @ApiOperation(value="修改密码接口", notes="修改密码接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/pwd/update")
    public JsonResponse updatePwd(@Valid @RequestBody UpdatePwdReq updatePwdReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(StringUtil.isNil(updatePwdReq.getOldPass())){
            return WebUtil.errorJsonResponse("旧密码不能为空");
        }
        String userId=tokenSessionCache.getUserId();
        String id=userService.updatePwd(userId,updatePwdReq);
        // 若已存在登录的，则先清除,修改密码之后，需要重新登录
        if (StringUtil.isNotNil(userTokenCache.getToken(userId))) {
            tokenSessionCache.invalid(userTokenCache.getToken(userId));
            userTokenCache.invalid(userId);
        }
        return WebUtil.successJsonResponse("修改密码成功");
    }


    /**
     * 手机号+密码 登录
     * @param loginReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="手机号+密码登录接口", notes="手机号+密码登录接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/mobile/login")
    public JsonResponse mobileLogin(@Valid @RequestBody LoginReq loginReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        UserLoginResp loginResp=userService.userLogin(loginReq);
        // 生成token
        String token = StringUtil.getUUID();
        logger.info("===>>>token：" + token);
        loginResp.setToken(token);
        // 若已存在登录的，则先清除，确保单一登录
        if (StringUtil.isNotNil(userTokenCache.getToken(loginResp.getId()))) {
            tokenSessionCache.invalid(userTokenCache.getToken(loginResp.getId()));
            userTokenCache.invalid(loginResp.getId());
        }
        // 保存TokenSession
        tokenSessionCache.putTokenSession(token, new TokenSessionDto(token,loginResp));
        userTokenCache.saveToken(loginResp.getId(), token);
        return WebUtil.successJsonResponse("登录成功",loginResp);
    }


    /**
     * 手机号注册
     * @param loginReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="手机号注册", notes="手机号注册", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/mobile/reg")
    public JsonResponse mobileReg(@Valid @RequestBody LoginReq loginReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (StringUtil.isNil(loginReq.getCode())){
            return WebUtil.errorJsonResponse("验证码不能为空");
        }
        if (smsCodeCache.codeIsValid(loginReq.getMobile(), loginReq.getCode())) {
            logger.info("\n 注册接口："+loginReq.getMobile()+"的验证码校验通过！");
            UserLoginResp loginResp =userService.save(loginReq);
            // 生成token
            String token = StringUtil.getUUID();
            logger.info("===>>>token：" + token);
            loginResp.setToken(token);
            // 若已存在登录的，则先清除，确保单一登录
            if (StringUtil.isNotNil(userTokenCache.getToken(loginResp.getId()))) {
                tokenSessionCache.invalid(userTokenCache.getToken(loginResp.getId()));
                userTokenCache.invalid(loginResp.getId());
            }
            // 保存TokenSession
            tokenSessionCache.putTokenSession(token, new TokenSessionDto(token,loginResp));
            userTokenCache.saveToken(loginResp.getId(), token);
            //注册成功并且邀请码不为空，那就说明需要发送mq消息，对邀请人增加糖果数
            if(StringUtil.isNotNil(loginReq.getInvetCode())){
                UserMQSender.send(loginReq.getInvetCode());
            }
            return WebUtil.successJsonResponse("注册成功",loginResp);
        }
        return WebUtil.errorJsonResponse("验证码错误");


    }


    @ApiOperation(value="第三方登录接口", notes="第三方登录接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/third/login")
    @HalfOpen
    public JsonResponse thirdLogin(@Valid @RequestBody UserDto userDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        UserLoginResp loginResp =userService.thirdLogin(userDto);
        // 生成token
        String token = StringUtil.getUUID();
        logger.info("===>>>token：" + token);
        loginResp.setToken(token);
        // 若已存在登录的，则先清除，确保单一登录
        if (StringUtil.isNotNil(userTokenCache.getToken(loginResp.getId()))) {
            tokenSessionCache.invalid(userTokenCache.getToken(loginResp.getId()));
            userTokenCache.invalid(loginResp.getId());
        }
        // 保存TokenSession
        tokenSessionCache.putTokenSession(token, new TokenSessionDto(token,loginResp));
        userTokenCache.saveToken(loginResp.getId(), token);
        return WebUtil.successJsonResponse("登录成功",loginResp);
    }


}
