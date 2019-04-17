package com.qkl.manage.admin.web;

import com.google.common.collect.Sets;
import com.qkl.admin.dto.RoleDto;
import com.qkl.admin.dto.RoleReq;
import com.qkl.admin.entity.Role;
import com.qkl.admin.service.RoleService;
import com.qkl.common.bean.BaseStatus;
import com.qkl.common.permission.Permission;
import com.qkl.common.permission.PermissionGroup;
import com.qkl.common.permission.PermissionScanner;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminSingleSession adminSingleSession;


    @RequestPermission(value = AdminPermission.ROLE)
    @MenuMapping(value = "角色管理", menu = BoardMenu.ADMIN, weight = 9)
    @GetMapping
    public String roles() {
        return "role/list";
    }


    /**
     * 前往新增/编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestPermission(value = AdminPermission.ROLE)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Role role = null;
        if (!"new".equals(id)) {
            model.addAttribute("role", (role = roleService.get(id)));
        }

        model.addAttribute("permissionGroups", this.getPermissionGroups(role));
        model.addAttribute("status", BaseStatus.getPropertyList(BaseStatus.class));

        /*List<Role> roleList = Lists.newArrayList();
        for (int i=0; i<110; i++) {
            roleList.add(getRole("批量测试"+(i+1)));
        }

        roleService.batchSave(roleList);*/

        return "role/edit";
    }

    private Role getRole(String roleName) {
        Role r = new Role();
        r.setName(roleName);
        r.setStatus(BaseStatus.COMMON);
        r.setPermissions(Sets.newHashSet());
        return r;
    }

    private List<Map<String,Object>> getPermissionGroups(Role role){

        List<Map<String,Object>> list = new ArrayList<>();

        List<PermissionGroup> pgList = PermissionScanner.getPermissionGroups();

        List<String> ps = null;
        if (role == null || role.getPermissions().isEmpty()) {
            ps = new ArrayList<>();
        } else {
            ps = role.getPermissions().stream().map(rp -> rp.getPermission()).collect(Collectors.toList());
        }

        for (PermissionGroup pg : pgList) {
            Map<String,Object> mpg = new HashMap<>();
            mpg.put("code", pg.getCode());
            mpg.put("name", pg.getName());
            List<Map<String,Object>> mper= new ArrayList<>();
            for (Permission p : pg.getPermissions()) {
                Map<String,Object> mp = new HashMap<>();
                mp.put("code", p.getCode());
                mp.put("name", p.getName());
                mp.put("have", ps.contains(p.getCode()) ? true : false);
                mper.add(mp);
            }
            mpg.put("permissions", mper);
            list.add(mpg);
        }
        return list;
    }

    @RequestPermission(value = AdminPermission.ROLE)
    @PostMapping("/save")
    @ResponseBody
    public JsonResponse save(RoleDto roleDto, String[] permissionCodes) {
        roleService.save(roleDto,permissionCodes);
        return WebUtil.successJsonResponse("保存成功");
    }

    @RequestPermission(value = AdminPermission.ROLE)
    @PostMapping("/delete/{id}")
    @ResponseBody
    public JsonResponse delete(@PathVariable String id) {
        if (StringUtils.isNotBlank(id)) {
            roleService.delete(id);
        }
        return WebUtil.successJsonResponse("删除成功");
    }

    @RequestPermission(value = AdminPermission.ROLE)
    @PostMapping("/data")
    @ResponseBody
    public Page<RoleDto> data(@RequestParam(value = "searchText", required = false) String searchText) {
        RoleReq roleReq = new RoleReq();
        roleReq.setQueryLike(searchText);
        roleReq.setPageSize(getPageRequest().getPageSize());
        roleReq.setPageNumber(getPageRequest().getPageNumber());
        Page<RoleDto> pageData = roleService.findPage(roleReq);
        return pageData;
    }


    /**
     * 激活角色/更改状态
     * @param id
     * @param enable
     * @return
     */
    @RequestPermission(value = AdminPermission.ROLE)
    @GetMapping("/activate/{id}/{enable}")
    @ResponseBody
    public String activate(@PathVariable String id,@PathVariable Boolean enable){
        String returnId = roleService.updateStatus(id, enable);
        if(returnId == null){
            return "不存在该角色数据";
        }
        return "操作成功";
    }

}
