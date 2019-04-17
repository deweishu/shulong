package com.qkl.manage.admin.web;


import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.admin.dto.ConfigReq;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 系统配置管理
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController {

	@Autowired
	private ConfigService configService;

	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.SYS_CONFIG)
	@MenuMapping(value = "系统参数配置", menu = BoardMenu.ADMIN, weight = 10)
	public String index() {
		return "config/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<ConfigDto> data(@RequestParam(value = "searchText", required = false) String searchText) { 
		ConfigReq configReq = new ConfigReq();
		configReq.setQueryLike(searchText);
		configReq.setPageSize(getPageRequest().getPageSize());
		configReq.setPageNumber(getPageRequest().getPageNumber());
		Page<ConfigDto> pageData = configService.findPage(configReq);
		return pageData;
	}



	/**
	* 进入新增页面
	*
	*/
	@GetMapping("/edit/{id}")
	public String toSave(Model model,@PathVariable String id) {
		ConfigDto configDto=configService.findOne(id);
		model.addAttribute("config",configDto);
		return "config/edit";
	}
	/**
	* 添加数据方法
	*/
	@SystemLogAnnotation(desc = "修改了系统参数配置")
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(ConfigDto configDto) {
		configService.save(configDto);
		return WebUtil.successJsonResponse("更新成功");
	}


}
