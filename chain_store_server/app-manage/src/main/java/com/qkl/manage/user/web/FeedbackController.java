package com.qkl.manage.user.web;


import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import com.qkl.user.dto.FeedbackReq;
import com.qkl.user.dto.FeedbackDto;
import com.qkl.user.service.FeedbackService;
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
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.MEMBER_REQUESTION)
	@MenuMapping(value = "用户反馈信息", menu = BoardMenu.MEMBER, weight = 9)
	public String index() {
		return "feedback/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<FeedbackDto> data(FeedbackReq feedbackReq) {
		feedbackReq.setPageSize(getPageRequest().getPageSize());
		feedbackReq.setPageNumber(getPageRequest().getPageNumber());
		Page<FeedbackDto> pageData = feedbackService.findPage(feedbackReq);
		return pageData;
	}


	@RequestPermission(value = AdminPermission.MEMBER_REQUESTION)
	@ResponseBody
	@GetMapping("/del/{id}")
	public JsonResponse delFeedBack(@PathVariable String id){
		feedbackService.delete(id);
		return WebUtil.successJsonResponse("操作成功");
	}

}
