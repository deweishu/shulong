package com.qkl.manage.user.web;


import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import com.qkl.user.dto.UserReq;
import com.qkl.user.dto.UserDto;
import com.qkl.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.MEMBER)
	@MenuMapping(value = "会员管理", menu = BoardMenu.MEMBER, weight = 9)
	public String index() {
		return "user/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<UserDto> data(UserReq userReq) {
		userReq.setPageSize(getPageRequest().getPageSize());
		userReq.setPageNumber(getPageRequest().getPageNumber());
		Page<UserDto> pageData = userService.findPage(userReq);
		return pageData;
	}




	@GetMapping("/invet/index/{userId}")
	@RequestPermission(value = AdminPermission.MEMBER)
	public String invetIndex(@PathVariable String userId,Model model) {
		model.addAttribute("invetId",userId);
		return "user/invet_list";
	}


	/**
	 * 分页请求数据
	 */
	@ResponseBody
	@PostMapping("/invet/data")
	public Page<UserDto> invetData(UserReq userReq) {
		userReq.setPageSize(getPageRequest().getPageSize());
		userReq.setPageNumber(getPageRequest().getPageNumber());
		Page<UserDto> pageData = userService.findPage(userReq);
		return pageData;
	}

	/**
	 * 用户状态操作
	 * @return
	 */
	@ResponseBody
	@GetMapping("/status/{id}/{status}")
	public JsonResponse userStatus(@PathVariable String id,@PathVariable Boolean status){
		return WebUtil.successJsonResponse("操作成功",userService.updateStatus(id,status));
	}




}
