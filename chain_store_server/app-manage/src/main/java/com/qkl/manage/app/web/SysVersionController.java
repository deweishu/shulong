package com.qkl.manage.app.web;

import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.dto.VersionDto;
import com.qkl.apk.dto.VersionReq;
import com.qkl.apk.service.VersionService;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.app.dto.ApkStatusReq;
import com.qkl.manage.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 系统应用apk，version 版本管理
 */
@Controller
@RequestMapping("/sys/version")
public class SysVersionController extends BaseController {


    @Autowired
    private VersionService versionService;

    @Autowired
    OSSManager ossManager;

    @RequestPermission(value = AdminPermission.SYS_APP_VERSION)
    @GetMapping("/index/{apkId}")
    public String index(@PathVariable String apkId, Model model) {
        model.addAttribute("apkId",apkId);
        return "version/sys_version_list";
    }


    /**
     * 分页请求数据
     */
    @ResponseBody
    @PostMapping("/data")
    public Page<VersionDto> data(VersionReq versionReq) {
        versionReq.setPageSize(getPageRequest().getPageSize());
        versionReq.setPageNumber(getPageRequest().getPageNumber());
        Page<VersionDto> pageData = versionService.findPage(versionReq);
        return pageData;
    }

    /**
     * 更改版本信息状态
     * @param apkStatusReq
     * @return
     */
    @RequestPermission(value = AdminPermission.SHENHE_DOWN_UP_VERSION)
    @SystemLogAnnotation(desc = "系统应用管理-修改了APP版本状态")
    @ResponseBody
    @PostMapping("/status")
    public JsonResponse updateStatus(@RequestBody ApkStatusReq apkStatusReq){
        try {
            versionService.updateStatus(apkStatusReq.getStatus(),apkStatusReq.getId(),
                    apkStatusReq.getStatusTxt());
        } catch (Exception e) {
            e.printStackTrace();
            return WebUtil.errorJsonResponse("操作失败");
        }
        return WebUtil.successJsonResponse("操作成功");
    }

}
