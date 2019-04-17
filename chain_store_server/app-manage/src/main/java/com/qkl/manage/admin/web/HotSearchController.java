package com.qkl.manage.admin.web;

import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.assembler.PubOperLogAssembler;
import com.qkl.admin.dto.HotSearchDto;
import com.qkl.admin.dto.PubOperLogDto;
import com.qkl.admin.dto.PubOperLogReq;
import com.qkl.admin.service.HotSearchService;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 热搜关键词维护
 */
@Controller
@RequestMapping("/hotsearch")
public class HotSearchController extends BaseController {


    @Autowired
    HotSearchService hotSearchService;


    @RequestPermission(value = AdminPermission.HOT_SEARCH_LIST)
    @MenuMapping(value = "热搜关键词维护", menu = BoardMenu.ADMIN, weight = 20)
    @GetMapping("/index")
    public String logs() {
        return "admin/hot_search";
    }


    @RequestPermission(value = AdminPermission.HOT_SEARCH_LIST)
    @PostMapping("/data")
    @ResponseBody
    public Page<HotSearchDto> data() {
        PageRequest pageRequest= new PageRequest(1,100);
        Page<HotSearchDto> dtoPage = new PageImpl<>(hotSearchService.getHotSearchKeyList() , pageRequest,100);
        return dtoPage;
    }

    /**
     * 添加热搜关键词
     * @param key
     * @return
     */
    @ResponseBody
    @SystemLogAnnotation(desc = "添加了热搜关键词")
    @GetMapping("/add/{key}")
    public JsonResponse addKey(@PathVariable String key){
        hotSearchService.save(key);
        return WebUtil.successJsonResponse("添加成功!");
    }



    /**
     * 删除热搜关键词
     * @param key
     * @return
     */
    @ResponseBody
    @SystemLogAnnotation(desc = "删除了热搜关键词")
    @GetMapping("/delete/{key}")
    public JsonResponse deleteKey(@PathVariable String key){
        hotSearchService.delete(key);
        return WebUtil.successJsonResponse("删除成功!");
    }

}
