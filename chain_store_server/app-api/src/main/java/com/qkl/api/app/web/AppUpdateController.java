package com.qkl.api.app.web;

import com.qkl.admin.service.AppVersionService;
import com.qkl.api.app.dto.AppUpdateReq;
import com.qkl.api.base.web.BaseController;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/app/update")
public class AppUpdateController extends BaseController {


    @Autowired
    AppVersionService appVersionService;


    @PostMapping("/check")
    @HalfOpen
    public JsonResponse checkUpdate(@Valid @RequestBody AppUpdateReq appUpdateReq, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return WebUtil.successJsonResponse("获取信息成功",appVersionService.checkUpdate(appUpdateReq.getClientType(),appUpdateReq.getVersionName()));
    }

}
