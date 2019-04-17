package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.user.cache.SignCache;
import com.qkl.user.cache.SignDayCache;
import com.qkl.user.service.SignLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 签到处理
 * @author dengjihai
 * @create 2018-08-29
 **/
@Api(description = "用户签到模块接口")
@RestController
@RequestMapping("/sign")
public class UserSignController extends BaseController {

    @Autowired
    SignLogService signLogService;

    @Autowired
    SignCache signCache;

    @Autowired
    SignDayCache signDayCache;

    @Autowired
    TokenSessionCache tokenSessionCache;


    @ApiOperation(value="签到获取糖果接口", notes="签到获取糖果接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/operate")
    public JsonResponse userSign(HttpServletRequest request){
        String userId=tokenSessionCache.getUserId();
        String date = TimeUtil.dateToString(new Date(), TimeUtil.datePattern);
        String cacheSign=signCache.getSign(userId);
        if(cacheSign!=null && cacheSign.equals(date)){
            return WebUtil.errorJsonResponse("当日已签到");
        }
        signLogService.save(userId, StringUtil.getIpAddr(request));
        return WebUtil.successJsonResponse("签到成功");
    }


    @ApiOperation(value="获取累计签到天数", notes="获取累计签到天数", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/day/num")
    public JsonResponse getSignDay(){
        String userId=tokenSessionCache.getUserId();
        return WebUtil.successJsonResponse("获取签到天数成功",signDayCache.getDayNum(userId));
    }





}
