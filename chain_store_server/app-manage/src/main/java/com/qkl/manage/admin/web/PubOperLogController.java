package com.qkl.manage.admin.web;

import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import com.qkl.admin.dto.PubOperLogReq;
import com.qkl.admin.dto.PubOperLogDto;
import com.qkl.admin.service.PubOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Benson on 2018/3/13.
 */
@Controller
@RequestMapping("/sys/operlog")
public class PubOperLogController extends BaseController {

    @Autowired
    private PubOperLogService pubOperLogService;


    @RequestPermission(value = AdminPermission.SYS_LOG_LIST)
    @MenuMapping(value = "系统操作日志", menu = BoardMenu.ADMIN, weight = 1)
    @GetMapping
    public String logs() {
        return "admin/sys_operlog_list";
    }


    @RequestPermission(value = AdminPermission.SYS_LOG_LIST)
    @PostMapping("/data")
    @ResponseBody
    public Page<PubOperLogDto> data(@RequestParam(value = "searchText", required = false) String searchText) {
        PubOperLogReq sysStatusReq = new PubOperLogReq();
        sysStatusReq.setQueryLike(searchText);
        sysStatusReq.setPageSize(getPageRequest().getPageSize());
        sysStatusReq.setPageNumber(getPageRequest().getPageNumber());
        Page<PubOperLogDto> pageData = pubOperLogService.findPage(sysStatusReq);
        return pageData;
    }
    
}
