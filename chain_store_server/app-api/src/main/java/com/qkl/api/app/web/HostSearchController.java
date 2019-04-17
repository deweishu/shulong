package com.qkl.api.app.web;

import com.qkl.admin.service.HotSearchService;
import com.qkl.api.base.web.BaseController;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热搜关键词管理
 */
@Api(description = "热搜关键词接口")
@RequestMapping("/hotsearch")
@RestController
public class HostSearchController extends BaseController {

    @Autowired
    HotSearchService hotSearchService;

    @ApiOperation(value="获取热搜关键词接口", notes="获取热搜关键词接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/get/all")
    public JsonResponse getSearchKey() {
        return WebUtil.successJsonResponse("获取热搜关键词成功",hotSearchService.getHotSearchKeyList());
    }
}
