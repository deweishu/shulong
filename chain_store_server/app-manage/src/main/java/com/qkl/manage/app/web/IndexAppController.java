package com.qkl.manage.app.web;

import com.qkl.apk.cache.AppIndexCache;
import com.qkl.apk.cache.TodayAppCache;
import com.qkl.apk.dto.ApkDto;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页APP维护controller
 * @author dengjihai
 * @create 2018-09-11
 **/
@Controller
@RequestMapping("/index/app")
public class IndexAppController extends BaseController {

    @Autowired
    AppIndexCache appIndexCache;



    @GetMapping("/index")
    @RequestPermission(value = AdminPermission.INDEX_APP)
    @MenuMapping(value = "首页APP维护", menu = BoardMenu.APP_MARKET, weight = 5)
    public String index(Model model,String date) {
        if(StringUtil.isNil(date)){
            model.addAttribute("date",TimeUtil.dateFormat(TimeUtil.now()));
        }else{
            model.addAttribute("date",date);
        }
        return "apk/index_app";
    }


    /**
     * 分页请求数据
     */
    @ResponseBody
    @PostMapping("/data/{date}")
    public List<ApkDto> data(@PathVariable String date) {
        List<ApkDto> apkDtoList=appIndexCache.getApkList(date);
        return apkDtoList;
    }


    /**
     * 删除首页APP操作
     * @param date
     * @param id
     * @return
     */
    @GetMapping("/delete/{date}/{id}")
    @ResponseBody
    public JsonResponse deleteToday(@PathVariable String date,@PathVariable String id){
        appIndexCache.delete(date,id);
        return WebUtil.successJsonResponse("操作成功");
    }
}
