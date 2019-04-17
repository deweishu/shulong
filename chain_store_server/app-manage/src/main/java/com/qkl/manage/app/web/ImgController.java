package com.qkl.manage.app.web;


import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.apk.dto.ImgReq;
import com.qkl.apk.dto.ImgDto;
import com.qkl.apk.service.ImgService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/img")
public class ImgController extends BaseController {

	@Autowired
	private ImgService imgService;

	@Autowired
	ConfigService configService;

	@GetMapping("/index/{apkId}")
	public String index(@PathVariable String apkId,Model model) {
		model.addAttribute("objectId",apkId);
		ConfigDto configDto=configService.getConfigByKey(SysConfigKey.APP_IMG_MAX_NUM.getCode());
		model.addAttribute("num",configDto.getConfigNum());
		return "apk/image/edit_list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<ImgDto> data(ImgReq imgReq) {
		imgReq.setPageSize(getPageRequest().getPageSize());
		imgReq.setPageNumber(getPageRequest().getPageNumber());
		Page<ImgDto> pageData = imgService.findPage(imgReq);
		return pageData;
	}


	/**
	 * 删除图片操作
	 * @param id
	 * @return
	 */
	@RequestPermission(value = AdminPermission.MY_APP_LIST)
	@ResponseBody
	@GetMapping("/delete/{id}")
	public String deleteImg(@PathVariable String id){
		String ids []= id.split(",");
		try {
			imgService.deleteImg(Arrays.asList(ids));
			return "操作成功";
		}catch (Exception e){
			e.printStackTrace();
			return "操作失败";
		}
	}

	/**
	 * 更改图片排序
	 * @param id
	 * @param sortNum
	 * @return
	 */
	@RequestPermission(value = AdminPermission.MY_APP_LIST)
	@ResponseBody
	@GetMapping("/update/sort/{id}/{sortNum}")
	public String updateSort(@PathVariable String id,@PathVariable Integer sortNum){
		try {
			imgService.updateSort(id,sortNum);
			return "操作成功";
		}catch (Exception e){
			e.printStackTrace();
			return "操作失败";
		}
	}



}
