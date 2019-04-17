package com.qkl.manage.admin.web;

import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dengjihai
 * @create 2018-08-29
 **/
@Controller
public class ForbiddenController extends BaseController {

    @GetMapping("/forbidden")
    @ResponseBody
    public JsonResponse forbidden(){
        return WebUtil.successJsonResponse("您无权访问该功能，请联系管理员授权");
    }
}
