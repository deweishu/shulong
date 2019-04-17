package com.qkl.manage.app.web;

import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.cache.CateRecommdCache;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.service.ApkService;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.app.dto.ApkZtReq;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.soap.Addressing;
import java.util.List;

/**
 * 分类推荐，管理
 * @author dengjihai
 * @create 2018-10-18
 **/
@Controller
@RequestMapping("/cate/recommed")
public class CateRecommedController extends BaseController {


    @Autowired
    CateRecommdCache cateRecommdCache;


    @Autowired
    ApkService apkService;


    @GetMapping("/index/{cateId}")
    @RequestPermission(value = AdminPermission.CATE_RECOMMED_APP)
    public String index(Model model, @PathVariable String cateId) {
       model.addAttribute("cateId",cateId);
        return "apk/cate_recommed_app";
    }



    /**
     * 分页请求数据
     */
    @ResponseBody
    @PostMapping("/data/{cateId}")
    public List<ApkDto> data(@PathVariable String cateId) {
        List<ApkDto> apkDtoList=cateRecommdCache.getRecommedListByCateId(cateId);
        return apkDtoList;
    }

    @SystemLogAnnotation(desc = "添加应用到分类推荐")
    @ResponseBody
    @PostMapping("/add")
    public JsonResponse addApkToRecommend(@RequestBody ApkZtReq apkZtReq){
        List<ApkDto> apkDtoList=apkService.findByIdIn(apkZtReq.getApkIds());
        try{
            cateRecommdCache.saveToRecommed(apkZtReq.getCateId(),apkDtoList);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return WebUtil.errorJsonResponse("操作失败");
        }
        return WebUtil.successJsonResponse("操作成功");
    }


    /**
     * 删除分类推荐缓存操作
     * @param cateId
     * @param id
     * @return
     */
    @SystemLogAnnotation(desc = "从分类推荐删除应用")
    @GetMapping("/delete/{cateId}/{id}")
    @ResponseBody
    public JsonResponse deleteToday(@PathVariable String cateId, @PathVariable String id){
        cateRecommdCache.delete(cateId,id);
        return WebUtil.successJsonResponse("操作成功");
    }
}
