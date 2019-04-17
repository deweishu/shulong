package com.qkl.manage.admin.web;

import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.dto.AdminDto;
import com.qkl.admin.dto.AdminReq;
import com.qkl.admin.entity.Admin;
import com.qkl.admin.entity.Role;
import com.qkl.admin.service.AdminService;
import com.qkl.admin.service.RoleService;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.AppException;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.admin.dto.UpdatePwdReq;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminSingleSession adminSingleSession;


    @RequestPermission(value = AdminPermission.ADMIN)
    @MenuMapping(value = "管理员管理", menu = BoardMenu.ADMIN, weight = 10)
    @GetMapping
    public String admins() {
        return "admin/list";
    }


    /**
     * 前往新增/编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestPermission(value = AdminPermission.ADMIN)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        if (!"new".equals(id)) {
            Admin admin = adminService.get(id);
            if (null==admin) throw new AppException("无效的ID值，找不到目标对象");
            List<Role> roles = new ArrayList<Role>();
            roles.addAll(admin.getRoles());
            model.addAttribute("admin", admin);
            model.addAttribute("myRoles", roles);
        }else{
            model.addAttribute("myRoles",  new ArrayList<>());
        }
        model.addAttribute("roles", roleService.getAvailable());
        //model.addAttribute("status", BaseStatus.getPropertyList(BaseStatus.class));
        return "admin/edit";
    }

    @RequestPermission(value = AdminPermission.ADMIN)
    @PostMapping("/save")
    @ResponseBody
    public JsonResponse save(AdminDto adminDto, String[] roleIds) {
        if (StringUtil.isNil(adminDto.getId())) {
            Admin oa = adminService.getByUsername(adminDto.getMobile());
            if (oa != null) {
                logger.info("该用户名经存在");
                return WebUtil.errorJsonResponse("该用户名经存在");
            }
        }

        adminService.save(adminDto, roleIds);
        return WebUtil.successJsonResponse("保存成功");
    }

    @RequestPermission(value = AdminPermission.ADMIN)
    @PostMapping("/delete/{id}")
    @ResponseBody
    public JsonResponse delete(@PathVariable String id) {
        if (StringUtil.isNotNil(id)) {
            adminService.delete(id);
            //TODO 清除被删除用的的redis信息
        }
        return WebUtil.successJsonResponse("管理员删除成功");
    }

    @RequestPermission(value = AdminPermission.ADMIN)
    @PostMapping("/data")
    @ResponseBody
    public Page<AdminDto> data(@RequestParam(value = "searchText", required = false) String searchText) {
        AdminReq adminReq = new AdminReq();
        adminReq.setQueryLike(searchText);
        adminReq.setPageSize(getPageRequest().getPageSize());
        adminReq.setPageNumber(getPageRequest().getPageNumber());
        Page<AdminDto> pageData = adminService.findPage(adminReq);
        return pageData;
    }


    /**
     * 密码修改页面
     * @return
     */
    @GetMapping("/update/pwd")
    public String updatePwdPage() {
        return "admin/updatePwd";
    }

    /**
     * 修改用户密码
     * @param updatePwdReq
     */
    @SystemLogAnnotation(desc = "修改了管理员密码")
    @PostMapping("/update/pwd")
    @ResponseBody
    public JsonResponse updatePwd(UpdatePwdReq updatePwdReq) {
        if (null==updatePwdReq) {
            return WebUtil.errorJsonResponse("参数不能为空");
        }
        if (StringUtil.isNil(updatePwdReq.getOriginalPwd()) || StringUtil.isNil(updatePwdReq.getNewPwd()) || StringUtil.isNil(updatePwdReq.getConfirmPwd())) {
            return WebUtil.errorJsonResponse("原密码、新密码和确认密码不能为空");
        }
        if (!updatePwdReq.getNewPwd().equals(updatePwdReq.getConfirmPwd())) {
            return WebUtil.errorJsonResponse("新密码和确认密码不匹配");
        }

        Admin admin = adminSingleSession.getUser();
        if (null == admin) {
            return WebUtil.errorJsonResponse("用户信息不能为空");
        }

        adminService.updatePwd(admin.getId(),updatePwdReq.getOriginalPwd(),updatePwdReq.getNewPwd());

        return WebUtil.successJsonResponse("修改成功!");
    }


    /**
     * 激活管理员/更改状态
     * @param id
     * @param enable
     * @return
     */
    @SystemLogAnnotation(desc = "更改了管理员的状态")
    @RequestPermission(value = AdminPermission.ADMIN)
    @GetMapping("/activate/{id}/{enable}")
    @ResponseBody
    public String activate(@PathVariable String id,@PathVariable Boolean enable){
        String returnId = adminService.updateStatus(id, enable);
        if(returnId == null){
            return "不存在该管理员数据";
        }
        return "操作成功";
    }

}
