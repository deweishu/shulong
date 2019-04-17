package com.qkl.background.web;

import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dengjihai
 * @create 2018-09-11
 **/
@Controller
public class BackGroundController  {


    @GetMapping({"/index","/"})
    public JsonResponse index(){
        return WebUtil.successJsonResponse("恭喜，请求成功！");
    }
}
