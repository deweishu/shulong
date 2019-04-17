package com.qkl.api.activity.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.apk.bean.AcitivityType;
import com.qkl.apk.es.EsActivityOperate;
import com.qkl.apk.service.ActivityService;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  活动接口信息
 * @author dengjihai
 * @create 2018-08-28
 **/
@Api(description = "活动信息查询")
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    ActivityService activityService;


    @Autowired
    EsActivityOperate esActivityOperate;


    @ApiOperation(value="根据活动类型获取活动信息(10.项目活动20.糖果盒子30.赏金计划)", notes="根据活动类型获取活动信息(10.项目活动20.糖果盒子30.赏金计划)", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/list/{type}")
    @HalfOpen
    public JsonResponse activityList(@PathVariable Integer type){
        AcitivityType acitivityType= JpaProperty.getProperty(AcitivityType.class,type);
        Assert.notNull(acitivityType,"不存在该活动类型");
        return WebUtil.successJsonResponse("获取活动信息成功",esActivityOperate.getActivityList(acitivityType));
    }
}
