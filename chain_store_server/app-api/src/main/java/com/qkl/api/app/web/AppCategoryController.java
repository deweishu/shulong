package com.qkl.api.app.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.apk.cache.CateRecommdCache;
import com.qkl.apk.service.CategoryService;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 分类查询
 */
@Api(description = "应用分类查询")
@RestController
@RequestMapping("/category")
public class AppCategoryController extends BaseController {


    @Autowired
    CategoryService categoryService;

    @Autowired
    CateRecommdCache cateRecommdCache;

    @ApiOperation(value="获取所有应用分类信息", notes="获取所有应用分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/list")
    @HalfOpen
    public JsonResponse getCategoryList() {
        return WebUtil.successJsonResponse("获取分类信息成功",categoryService.getCategoryList());
    }

    @HalfOpen
    @ApiOperation(value="根据专题获取APP(9.新品推荐10.入门必备11.今日主题)", notes="根据专题获取APP(9.新品推荐10.入门必备11.今日主题)", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/zt/{id}")
    public JsonResponse getCateByZt(@PathVariable String id){
        return WebUtil.successJsonResponse("根据应用专题获取APP列表",categoryService.getApkListByZt(id,false));
    }


    @HalfOpen
    @ApiOperation(value="根据应用分类ID获取推荐APP", notes="根据应用分类ID获取推荐APP", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/recommed/{cateId}")
    public JsonResponse getCateRecommenList(@PathVariable String cateId){
        return WebUtil.successJsonResponse("获取推荐APP成功",cateRecommdCache.getRecommedListByCateId(cateId));
    }
}
