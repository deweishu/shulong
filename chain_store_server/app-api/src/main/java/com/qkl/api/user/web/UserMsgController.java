package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.msg.jpa.UserMsgService;
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
 * @author dengjihai
 * @create 2018-10-31
 **/
@Api(description = "用户消息模块")
@RestController
@RequestMapping("/user/msg")
public class UserMsgController extends BaseController {


    @Autowired
    UserMsgService msgService;

    @Autowired
    TokenSessionCache tokenSessionCache;

    @ApiOperation(value="查询消息列表接口，无分页", notes="查询消息列表接口，无分页", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/list")
    public JsonResponse getMsgList(){
        String userId=tokenSessionCache.getUserId();
        return WebUtil.successJsonResponse("查询消息成功",msgService.findByUserId(userId));
    }




    @ApiOperation(value="设置消息已读", notes="设置消息已读", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/read/status/{msgId}")
    public JsonResponse updateStatus(@PathVariable String msgId){
        String userId=tokenSessionCache.getUserId();
        msgService.updateStatus(userId,msgId);
        return WebUtil.successJsonResponse("操作成功");
    }




    @ApiOperation(value="删除消息", notes="删除消息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/delete/{msgId}")
    public JsonResponse deleteMsg(@PathVariable String msgId){
        String userId=tokenSessionCache.getUserId();
        msgService.delete(userId,msgId);
        return WebUtil.successJsonResponse("操作成功");
    }

}
