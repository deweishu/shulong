package com.qkl.api.user.web;

import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.dto.SendCodeRequest;
import com.qkl.api.user.validate.GeetestConfig;
import com.qkl.api.user.validate.GeetestLib;
import com.qkl.common.mq.UserMQSender;
import com.qkl.common.util.EncryptionUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.AppException;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import com.qkl.msg.bean.SmsTemplate;
import com.qkl.msg.service.SmsRecordService;
import com.qkl.sms.cache.SmsCodeCache;
import com.qkl.sms.dto.SendSmsResult;
import com.qkl.user.dto.LoginReq;
import com.qkl.user.dto.UserDto;
import com.qkl.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author dengjihai
 * @create 2018-09-12
 **/
@Controller
@RequestMapping("/h5/user")
public class UserFrontController extends BaseController {


    @Autowired
    UserService userService;

    @Autowired
    ConfigService configService;


    @Autowired
    private SmsCodeCache smsCodeCache;
    @Autowired
    private SmsRecordService smsRecordService;


    /***
     * 加载验证码模块
     *
     */
    @ResponseBody
    @GetMapping("/gt/code")
    @HalfOpen
    public String validate(HttpServletRequest request){
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String resStr = "{}";
        String userid = "test";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", StringUtil.getIpAddr(request)); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        logger.info("\n ### 加载验证码，获取到结果：{}",resStr);
        return resStr;
    }

    /**
     * 进入邀请注册页面
     * @param model
     * @param invetCode
     * @return
     */
    @GetMapping("/register")
    @HalfOpen
    public String userReg(Model model, @RequestParam(name = "invetCode",required = false)String invetCode,HttpServletRequest request){
        ConfigDto configDto=configService.getConfigByKey(SysConfigKey.REG_CANDY.getCode());
        model.addAttribute("regCandyNum",configDto.getConfigNum());
        model.addAttribute("invetCode",invetCode);
        return "user/reg";
    }

    /**
     * 发送短信验证码，需要校验图形验证码
     * @param sendCodeRequest
     * @param bindingResult
     * @return
     */
    @PostMapping("/code")
    @HalfOpen
    public JsonResponse sendCode(@Valid @RequestBody SendCodeRequest sendCodeRequest, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        UserDto invetUser=userService.findByInvetCode(sendCodeRequest.getInvetCode());
        Assert.notNull(invetUser,"邀请码无效");//不存在该邀请码
        Assert.isTrue(invetUser.getStatus(),"邀请码无效");//邀请者被封号


//        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
//                GeetestConfig.isnewfailback());
//
//        String challenge = sendCodeRequest.getGeetest_challenge();
//        String validate = sendCodeRequest.getFn_geetest_validate();
//        String seccode = sendCodeRequest.getFn_geetest_seccode();
//        if(StringUtil.isNil(challenge) || StringUtil.isNil(validate) || StringUtil.isNil(seccode)){
//            return  WebUtil.errorJsonResponse("参数不完整");
//        }
//        //从session中获取gt-server状态
//        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
//
//        //从session中获取userid
//        String userid = (String)request.getSession().getAttribute("userid");
//
//        //自定义参数,可选择添加
//        HashMap<String, String> param = new HashMap<>();
//        param.put("user_id", userid); //网站用户id
//        param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
//        param.put("ip_address",StringUtil.getIpAddr(request)); //传输用户请求验证时所携带的IP
//        int gtResult = 0;
//        if (gt_server_status_code == 1) {
//            //gt-server正常，向gt-server进行二次验证
//            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
//            logger.info("\n gt-server正常，向gt-server进行二次验证: {}",gtResult);
//        } else {
//            // gt-server非正常情况下，进行failback模式验证
//            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
//            logger.info("\n gt-server非正常情况下，进行failback模式验证,{}",gtResult);
//        }


        if(StringUtil.isNil(sendCodeRequest.getImgCode())){
            return WebUtil.errorJsonResponse("图形验证码不能为空");
        }
        String rightCode = (String) request.getSession().getAttribute("rightCode");
        Assert.isTrue(rightCode!=null,"图形验证码错误");

        Assert.isTrue(rightCode.equals(sendCodeRequest.getImgCode()),"图形验证码错误");

        UserDto userDto=userService.findByMobile(sendCodeRequest.getMobile());
        Assert.isTrue(userDto==null,"该手机号已经注册过");

        SmsTemplate template = SmsTemplate.getTemplateByCode(sendCodeRequest.getModuleType());
        SendSmsResult sendSmsResult = smsRecordService.sendSmsCode(sendCodeRequest.getMobile(), template);
        if (sendSmsResult.isSuccess()) {
            request.getSession().removeAttribute("rightCode");
            return WebUtil.successJsonResponse("发送短信成功");
        } else {
            return WebUtil.errorJsonResponse(sendSmsResult.getMsg());
        }
    }

    /**
     * 提交注册请求
     * @param loginReq
     * @param bindingResult
     * @return
     */
    @PostMapping("/submit/reg")
    @HalfOpen
    public JsonResponse userRegister(@Valid @RequestBody LoginReq loginReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (smsCodeCache.codeIsValid(loginReq.getMobile(), loginReq.getCode())) {
            loginReq.setPassWord(EncryptionUtil.md5(loginReq.getPassWord()));
            userService.save(loginReq);
            //注册成功并且邀请码不为空，那就说明需要发送mq消息，对邀请人增加糖果数
            if(StringUtil.isNotNil(loginReq.getInvetCode())){
                UserMQSender.send(loginReq.getInvetCode());
            }
            return WebUtil.successJsonResponse("注册成功");
        }else{
            return WebUtil.errorJsonResponse("短信验证码错误");
        }
    }

}
