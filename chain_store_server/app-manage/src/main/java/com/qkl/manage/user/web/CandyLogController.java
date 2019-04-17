package com.qkl.manage.user.web;


import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.user.dto.CandyLogDto;
import com.qkl.user.dto.CandyLogReq;
import com.qkl.user.service.CandyLogService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 用户糖果数
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/candy")
public class CandyLogController extends BaseController {

	@Autowired
	private CandyLogService candyLogService;

	@GetMapping("/index/{userId}")
	@RequestPermission(value = AdminPermission.MEMBER_CADNY_LOG)
	public String index(@PathVariable String userId,Model model) {
		model.addAttribute("userId",userId);
		return "candyLog/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<CandyLogDto> data(CandyLogReq candyLogReq) {
		candyLogReq.setPageSize(getPageRequest().getPageSize());
		candyLogReq.setPageNumber(getPageRequest().getPageNumber());
		Page<CandyLogDto> pageData = candyLogService.findPage(candyLogReq);
		return pageData;
	}

}
