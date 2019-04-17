package com.qkl.api.config.web;

import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.api.base.web.BaseController;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-08-29
 **/
@Api(description = "系统配置信息接口")
@RestController
@RequestMapping("/config")
public class SysConfigController extends BaseController {


    @Autowired
    ConfigService configService;


    @ApiOperation(value="根绝配置key获取配置信息", notes="根绝配置key获取配置信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/info/{key}")
    @HalfOpen
    public JsonResponse getConfigInfo(@PathVariable String key){
        List<ConfigDto> configDtoList = new ArrayList<>();
        if(key.indexOf(",")>-1){
            String keys[]=key.split(",");
            for (int i=0;i<keys.length;i++){
                configDtoList.add(configService.getConfigByKey(keys[i]));
            }
        }else{
            configDtoList.add(configService.getConfigByKey(key));
        }
        return WebUtil.successJsonResponse("获取配置信息成功",configDtoList);
    }
}
