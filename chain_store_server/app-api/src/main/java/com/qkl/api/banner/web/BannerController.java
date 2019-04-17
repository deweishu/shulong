package com.qkl.api.banner.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.apk.service.BannerService;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;

/**
 * 首页banner查询
 */
@Api(description = "首页Banner查询接口")
@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController {

    @Autowired
    BannerService bannerService;

    @ApiOperation(value="首页获取banner信息接口", notes="首页获取banner信息接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/list")
    public JsonResponse bannerList(){
        return WebUtil.successJsonResponse("获取banner信息成功",bannerService.getBannerList());
    }

/*
    @ApiOperation(value = "首页获取banner信息接口",notes = "首页获取banner信息接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/list")
    public  JsonResponse bannerlist(){
        return WebUtil.successJsonResponse(successMessage:"获取banner信息成功",bannerService.getBannerList());
    }*/

}
