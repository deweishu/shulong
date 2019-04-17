package com.qkl.manage.admin.web;


import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.dto.CustomerDto;
import com.qkl.admin.dto.CustomerReq;
import com.qkl.admin.entity.Role;
import com.qkl.admin.service.CustomerService;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Set;


/**
* generate by dengjihai at 2018-08-24
 * 合作商管理
*/
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	AdminSingleSession adminSingleSession;

	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.PARTNER_LIST)
	@MenuMapping(value = "合作商管理", menu = BoardMenu.PARTNER, weight = 10)
	public String index() {
		return "customer/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<CustomerDto> data(CustomerReq customerReq) {
		Set<Role> roleSet=adminSingleSession.getUser().getRoles();
		Boolean isPartner=false;
		if(roleSet.size()==1){
			Iterator<Role> roleIterator=roleSet.iterator();
			while (roleIterator.hasNext()){
				Role role=roleIterator.next();
				if(role.getId().equals(CodeConstant.PARTNER_ROLE_ID)){
					isPartner=true;
				}
			}
		}

		if(isPartner){
			customerReq.setPhone(adminSingleSession.getUser().getMobile());
		}
		customerReq.setPageSize(getPageRequest().getPageSize());
		customerReq.setPageNumber(getPageRequest().getPageNumber());
		Page<CustomerDto> pageData = customerService.findPage(customerReq);
		return pageData;
	}


	/**
	 * 重置合作商密码
	 * @param id
	 * @return
	 */
	@SystemLogAnnotation(desc = "重置合作商密码")
	@RequestPermission(value = AdminPermission.PARTNER_REST_PWD)
	@RequestMapping("/rest/pwd/{id}")
	@ResponseBody
	public JsonResponse restPwd(@PathVariable String id){
		customerService.restPwd(id);
		return WebUtil.successJsonResponse("重置密码成功");
	}


	/**
	 * 进入修改页面
	 *
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(Model model,@PathVariable String id) {
		CustomerDto customerDto=customerService.findOne(id);
		model.addAttribute("customer",customerDto);
		return "customer/edit";
	}


	/**
	* 进入新增页面
	*
	*/
	@GetMapping("/add")
	public String toSave(Model model) {
		return "customer/edit";
	}
	/**
	* 添加数据方法
	*/
	@SystemLogAnnotation(desc = "更新/添加了合作商信息")
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(CustomerDto customerDto) {
		customerService.save(customerDto);
		return WebUtil.successJsonResponse("保存成功");
	}


}
