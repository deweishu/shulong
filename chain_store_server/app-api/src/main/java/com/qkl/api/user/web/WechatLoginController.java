package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.third.wechat.service.WechatLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengjihai
 * @create 2018-09-18
 **/
@RestController
@RequestMapping("/wechat")
public class WechatLoginController extends BaseController {


    @Autowired
    WechatLoginService wechatLoginService;

    /**
     * 微信登录接口
     * @return
     */
    @PostMapping("/login/{code}")
    public JsonResponse wechatLogin(@PathVariable String code){
        wechatLoginService.wechatLogin(code);

        return WebUtil.successJsonResponse("微信登录成功");
    }
}
