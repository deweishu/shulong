package com.dwshu.controller;

import com.dwshu.base.WechatBase;
import com.dwshu.pojo.User;
import com.dwshu.service.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.sns.SnsToken;

import java.util.List;


/**
 * @author dwshu
 * @create 2019/11/27
 */


@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserServer userServer;

    @Autowired
    private WechatBase wechatBase;

    /**
     * 普通登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        ModelAndView model = new ModelAndView();
        User user = userServer.findByUsernameAndPassword(username, password);
        if (user != null) {
            System.out.println("登陆成功");
            model.setViewName("bey");
        } else {
            System.out.println("登陆失败");
            model.setViewName("../static/index.html");
        }
        return model;
    }

    /**
     * 第三方登陆：微信登陆
     * @param code
     */
    @PostMapping("/threeLogin/{code}")
    public void threeLogin(@PathVariable String code) {
        //获取token
        SnsToken token = SnsAPI.oauth2AccessToken(wechatBase.getAppId(), wechatBase.getAppSecret(), code);
        //获取返回结果：reecode=="0"&&errmsg=="ok" 成功
        BaseResult auth = SnsAPI.auth(token.getAccess_token(), token.getOpenid());
        logger.info("\n ### 成功获得信信登录用户 user info {},:", auth.toString());
        if ("0".equals(auth.getErrcode()) && "ok".equals(auth.getErrmsg())) {
            weixin.popular.bean.user.User threeUser = SnsAPI.userinfo(token.getAccess_token(), token.getOpenid(), "zh_CN");
            System.out.println(threeUser.toString());
        }else {
            throw new RuntimeException("扫码失败，请稍后重试...");
        }

    }


    /**
     * 查询所有用户
     *
     * @return
     */
    @GetMapping("/all")
    public List<User> getAll() {
        List<User> users = userServer.findAllByOrderById();
        for (User user : users) {

            //将查出的用户信息，已Id为键，用户为值的方式存入redis中
            redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);
        }
        return users;
    }

}
