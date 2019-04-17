package com.qkl.manage.app.web;


import com.alibaba.fastjson.JSON;
import com.qkl.admin.annotaion.IngoreParam;
import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.bean.AcitivityType;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.enums.OssDirType;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.apk.dto.ActivityReq;
import com.qkl.apk.dto.ActivityDto;
import com.qkl.apk.service.ActivityService;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


/**
 * 花式玩法--活动管理
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;

	@Autowired
	OSSManager ossManager;



	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.ACTIVITY_LIST)
	@MenuMapping(value = "活动管理", menu = BoardMenu.ADVERT_ACTIVITY, weight = 2)
	public String index() {
		return "activity/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<ActivityDto> data(@RequestParam(value = "searchText", required = false) String searchText) { 
		ActivityReq activityReq = new ActivityReq();
		activityReq.setQueryLike(searchText);
		activityReq.setPageSize(getPageRequest().getPageSize());
		activityReq.setPageNumber(getPageRequest().getPageNumber());
		Page<ActivityDto> pageData = activityService.findPage(activityReq);
		return pageData;
	}


	/**
	 * 更改活动状态
	 * @param id
	 * @param status
	 * @return
	 */
	@SystemLogAnnotation(desc = "修改了活动状态")
	@ResponseBody
	@GetMapping("/status/{id}/{status}")
	public JsonResponse updateStatus(@PathVariable String id,@PathVariable Boolean status){
		try {
			activityService.updateStatus(status,id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("\n ####  活动信息 同步到es 信息失败");
		}
		return WebUtil.successJsonResponse("操作成功");
	}


	/**
	 * 进入活动详情页面
	 *
	 */
	@RequestPermission(value = AdminPermission.UPDATE_ACTIVITY_INFO)
	@GetMapping("/edit/{id}")
	public String toEdit(Model model,@PathVariable String id) {
		model.addAttribute("typeList", JpaProperty.getPropertyList(AcitivityType.class));
		model.addAttribute("activity",activityService.findOne(id));
		return "activity/edit";
	}

	/**
	* 进入新增页面
	*
	*/
	@RequestPermission(value = AdminPermission.UPDATE_ACTIVITY_INFO)
	@GetMapping("/add")
	public String toSave(Model model) {
		model.addAttribute("typeList", JpaProperty.getPropertyList(AcitivityType.class));
		return "activity/edit";
	}
	/**
	* 添加数据方法
	*/
	@SystemLogAnnotation(desc = "更新/添加了活动信息")
	@RequestPermission(value = AdminPermission.UPDATE_ACTIVITY_INFO)
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(@IngoreParam(value = true) @RequestParam("mainImgFile") MultipartFile mainImgFile,
							 @IngoreParam(value = true) @RequestParam("logoFile") MultipartFile logoFile,ActivityDto activityDto) throws IOException {
		logger.info("\n 接收到的数据:{}", JSON.toJSONString(activityDto));
		//主图
		if (mainImgFile != null && !mainImgFile.isEmpty() && mainImgFile.getSize() > 0) {
			InputStream is = mainImgFile.getInputStream();
			String fileName = mainImgFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String tempFileName = StringUtil.getUUID() +"."+ suffix;
			// 上传到OSS上
			String ossUrl = ossManager.upload(OssDirType.IMG_PATH, Boolean.TRUE, is, tempFileName);
			activityDto.setMainImg(ossUrl);
		}
		//logo
		if (logoFile != null && !logoFile.isEmpty() && logoFile.getSize() > 0) {
			InputStream is = logoFile.getInputStream();
			String fileName = logoFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String tempFileName = StringUtil.getUUID() +"."+ suffix;
			// 上传到OSS上
			String ossUrl = ossManager.upload(OssDirType.IMG_PATH, Boolean.TRUE, is, tempFileName);
			activityDto.setLogo(ossUrl);
		}
		try {
			activityService.save(activityDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("\n ####  活动信息 同步到es 信息失败");
		}
		return WebUtil.successJsonResponse("保存成功");
	}


}
