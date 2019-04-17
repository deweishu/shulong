package com.qkl.manage.user.web;


import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.user.dto.SignLogReq;
import com.qkl.user.dto.SignLogDto;
import com.qkl.user.service.SignLogService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 用户签到日志查询
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/sign")
public class SignLogController extends BaseController {

	@Autowired
	private SignLogService signLogService;

	@GetMapping("/index/{id}")
	@RequestPermission(value = AdminPermission.MEMBER_SIGN_LOG)
	public String index(@PathVariable String id,Model model) {
		model.addAttribute("userId",id);
		return "signLog/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<SignLogDto> data(SignLogReq signLogReq) {
		signLogReq.setPageSize(getPageRequest().getPageSize());
		signLogReq.setPageNumber(getPageRequest().getPageNumber());
		Page<SignLogDto> pageData = signLogService.findPage(signLogReq);
		return pageData;
	}


}
