package com.qkl.manage.app.web;

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
 * @author dengjihai
 * @create 2018-09-11
 **/
@Controller
@RequestMapping("/today/app")
public class TodayAppController extends BaseController {

    @Autowired
    TodayAppCache todayAppCache;



    @GetMapping("/index")
    @RequestPermission(value = AdminPermission.TODAY_APP)
    @MenuMapping(value = "今日主题", menu = BoardMenu.APP_MARKET, weight = 4)
    public String index(Model model,String date) {
        if(StringUtil.isNil(date)){
            model.addAttribute("date",TimeUtil.dateFormat(TimeUtil.now()));
        }else{
            model.addAttribute("date",date);
        }
        return "apk/today_app";
    }


    /**
     * 分页请求数据
     */
    @ResponseBody
    @PostMapping("/data/{date}")
    public List<ApkDto> data(@PathVariable String date) {
        List<ApkDto> apkDtoList=todayAppCache.getApkList(date);
        return apkDtoList;
    }


    /**
     * 删除今日主题操作
     * @param date
     * @param id
     * @return
     */
    @GetMapping("/delete/{date}/{id}")
    @ResponseBody
    public JsonResponse deleteToday(@PathVariable String date,@PathVariable String id){
        todayAppCache.delete(date,id);
        return WebUtil.successJsonResponse("操作成功");
    }
}
