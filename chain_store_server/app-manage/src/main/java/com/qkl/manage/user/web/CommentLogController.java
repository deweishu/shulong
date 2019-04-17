package com.qkl.manage.user.web;


import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.user.dto.CommentLogDto;
import com.qkl.user.dto.CommentLogReq;
import com.qkl.user.service.CommentLogService;
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
@RequestMapping("/comment")
public class CommentLogController extends BaseController {

	@Autowired
	private CommentLogService commentLogService;

	@GetMapping("/index/{apkId}")
	@RequestPermission(value = AdminPermission.APP_COMMENT_LIST)
	public String index(@PathVariable String apkId,Model model) {
		model.addAttribute("apkId",apkId);
		return "comment/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<CommentLogDto> data(CommentLogReq commentLogReq) {
		commentLogReq.setPageSize(getPageRequest().getPageSize());
		commentLogReq.setPageNumber(getPageRequest().getPageNumber());
		Page<CommentLogDto> pageData = commentLogService.findPage(commentLogReq);
		return pageData;
	}


	/**
	 * 进入新增页面
	 *
	 */
	@GetMapping("/add")
	public String toSave(Model model) {
		return "comment/edit";
	}
	/**
	 * 添加数据方法
	 */
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(CommentLogDto commentLogDto) {
		commentLogService.save(commentLogDto);
		return WebUtil.successJsonResponse("保存成功");
	}



	@RequestPermission(value = AdminPermission.APP_COMMENT_LIST)
	@ResponseBody
	@GetMapping("/del/{id}")
	public JsonResponse delComment(@PathVariable String id){
		commentLogService.delete(id);
		return WebUtil.successJsonResponse("操作成功");
	}




}
