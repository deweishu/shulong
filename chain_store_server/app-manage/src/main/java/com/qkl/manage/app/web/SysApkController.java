package com.qkl.manage.app.web;

import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.assembler.CategoryAssembler;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.ApkReq;
import com.qkl.apk.dto.ApkSortDto;
import com.qkl.apk.dto.CategoryDto;
import com.qkl.apk.service.ApkService;
import com.qkl.apk.service.CategoryService;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.app.dto.ApkStatusReq;
import com.qkl.manage.app.dto.ApkZtReq;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统应用App操作管理
 */
@Controller
@RequestMapping("/sys/apk")
public class SysApkController extends BaseController {

    @Autowired
    private ApkService apkService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/index")
    @RequestPermission(value = AdminPermission.SYS_ALL_APP)
    @MenuMapping(value = "系统所有应用", menu = BoardMenu.APP_MARKET, weight = 3)
    public String index(Model model) {
        model.addAttribute("cateList",categoryService.getCategoryList());
        model.addAttribute("statusList", JpaProperty.getPropertyList(ApkStatus.class));
        return "apk/sys_apk_list";
    }


    /**
     * 分页请求数据
     */
    @ResponseBody
    @PostMapping("/data")
    public Page<ApkDto> data(ApkReq apkReq) {
        apkReq.setPageSize(getPageRequest().getPageSize());
        apkReq.setPageNumber(getPageRequest().getPageNumber());
        Page<ApkDto> pageData = apkService.findPage(apkReq);
        return pageData;
    }



    @GetMapping("/zt/index/{ztId}")
    @RequestPermission(value = AdminPermission.APP_CATE_ADD)
    public String ztIndex(Model model,@PathVariable String ztId) {
        model.addAttribute("ztId",ztId);
        return "catgory/cate_apk_list";
    }


    /**
     * 分页请求数据
     */
    @ResponseBody
    @PostMapping("/zt/data/{ztId}")
    public Page<ApkDto> ztData(@PathVariable String ztId) {
        List<ApkDto> apkDtoList=categoryService.getApkListByZt(ztId,true);
        PageRequest pageRequest;
        if(apkDtoList.size()>0){
             pageRequest = new PageRequest(1,apkDtoList.size());
        } else{
             pageRequest = new PageRequest(1,1);
        }
        Page<ApkDto> pageData = new PageImpl<>(apkDtoList, pageRequest,apkDtoList.size());
        return pageData;
    }


    /**
     * 同步数据到es
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/async/{id}")
    public JsonResponse asyncToEs(@PathVariable String id){
        apkService.asyncApkToEs(id);
        return WebUtil.successJsonResponse("同步成功");
    }

    /**
     * 更改应用状态
     * @param apkStatusReq
     * @return
     */
    @RequestPermission(value = AdminPermission.SHENHE_APP)
    @SystemLogAnnotation(desc = "更改了系统应用的状态信息")
    @ResponseBody
    @PostMapping("/status")
    public JsonResponse updateStatus(@RequestBody ApkStatusReq apkStatusReq){
        try {
            apkService.updateStatus(apkStatusReq.getStatus(),apkStatusReq.getId(),
                    apkStatusReq.getStatusTxt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WebUtil.successJsonResponse("操作成功");
    }


    @RequestPermission(value = AdminPermission.APP_CATE_ADD)
    @SystemLogAnnotation(desc = "将应用加入到了专题模块/首页")
    @ResponseBody
    @PostMapping("/add/zt")
    public JsonResponse addToZhuanTi(@RequestBody ApkZtReq apkZtReq){

        categoryService.addApkToCate(apkZtReq.getApkIds(),apkZtReq.getCateId(),apkZtReq.getZtDate());

        return WebUtil.successJsonResponse("操作成功");
    }



    @RequestPermission(value = AdminPermission.APP_CATE_ADD)
    @SystemLogAnnotation(desc = "将应用加入到了应用/游戏榜")
    @ResponseBody
    @PostMapping("/add/sort")
    public JsonResponse addToSort(@RequestBody ApkSortDto apkSortDto){

        apkService.addApkToSort(apkSortDto);

        return WebUtil.successJsonResponse("操作成功");
    }



    @RequestPermission(value = AdminPermission.APP_CATE_ADD)
    @SystemLogAnnotation(desc = "将应用从专题中删除了")
    @ResponseBody
    @GetMapping("/delete/zt/{apkId}/{cateId}")
    public JsonResponse deleteToZhuanTi(@PathVariable String apkId,@PathVariable String cateId){
        categoryService.deleteApkToCate(apkId,cateId);
        return WebUtil.successJsonResponse("操作成功");
    }

    @RequestPermission(value = AdminPermission.APP_CATE_ADD)
    @SystemLogAnnotation(desc = "清除了应用专题的缓存")
    @ResponseBody
    @GetMapping("/clear/zt/cache/{id}")
    public JsonResponse clearCache(@PathVariable String id){
        categoryService.clearCateZtCache(id);
        return WebUtil.successJsonResponse("清除缓存成功");
    }


    @ResponseBody
    @GetMapping("/es/aysc")
    public JsonResponse asyncApk(){
        apkService.selfAsycApkToEs(ApkStatus.NORMAL);
        return WebUtil.successJsonResponse("同步成功");
    }

}
