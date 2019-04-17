package com.qkl.manage.app.web;


import com.qkl.admin.annotaion.IngoreParam;
import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.bean.BannerLinkType;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.enums.OssDirType;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.apk.dto.BannerReq;
import com.qkl.apk.dto.BannerDto;
import com.qkl.apk.service.BannerService;
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
 * banner管理
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerService bannerService;

	@Autowired
	OSSManager ossManager;

	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.INDEX_ADVERT_LIST)
	@MenuMapping(value = "广告banner", menu = BoardMenu.ADVERT_ACTIVITY, weight = 3)
	public String index() {
		return "banner/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<BannerDto> data(@RequestParam(value = "searchText", required = false) String searchText) { 
		BannerReq bannerReq = new BannerReq();
		bannerReq.setQueryLike(searchText);
		bannerReq.setPageSize(getPageRequest().getPageSize());
		bannerReq.setPageNumber(getPageRequest().getPageNumber());
		Page<BannerDto> pageData = bannerService.findPage(bannerReq);
		return pageData;
	}



	/**
	 * 进入编辑页面
	 *
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(Model model,@PathVariable String id) {
		model.addAttribute("banner",bannerService.findOne(id));
		model.addAttribute("typeList", JpaProperty.getPropertyList(BannerLinkType.class));
		return "banner/edit";
	}


	/**
	* 进入新增页面
	*
	*/
	@RequestPermission(value = AdminPermission.UPDATE_INDEX_ADVERT)
	@GetMapping("/add")
	public String toSave(Model model) {
		model.addAttribute("typeList", JpaProperty.getPropertyList(BannerLinkType.class));
		return "banner/edit";
	}

	/**
	 * 更改广告banner状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestPermission(value = AdminPermission.UPDATE_INDEX_ADVERT)
	@SystemLogAnnotation(desc = "修改了广告banner状态")
	@ResponseBody
	@GetMapping("/status/{id}/{status}")
	public JsonResponse updateStatus(@PathVariable String id,@PathVariable Boolean status){
		bannerService.updateStatus(status,id);
		return WebUtil.successJsonResponse("操作成功");
	}



	/**
	 * 删除广告
	 * @param id
	 * @return
	 */
	@RequestPermission(value = AdminPermission.DELETE_INDEX_ADVERT)
	@SystemLogAnnotation(desc = "删除了首页广告banner")
	@ResponseBody
	@GetMapping("/delete/{id}")
	public JsonResponse delete(@PathVariable String id){
		bannerService.delete(id);
		return WebUtil.successJsonResponse("操作成功");
	}


	/**
	* 添加数据方法
	*/
	@RequestPermission(value = AdminPermission.UPDATE_INDEX_ADVERT)
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(@IngoreParam(value = true) @RequestParam("logoFile") MultipartFile logoFile,
							 BannerDto bannerDto) throws IOException {
		if (!logoFile.isEmpty() && logoFile.getSize() > 0) {
			InputStream is = logoFile.getInputStream();
			String fileName = logoFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String tempFileName = StringUtil.getUUID() +"."+ suffix;
			// 上传到OSS上
			String ossUrl = ossManager.upload(OssDirType.IMG_PATH, Boolean.TRUE, is, tempFileName);
			bannerDto.setLogo(ossUrl);
		}
		bannerService.save(bannerDto);
		return WebUtil.successJsonResponse("保存成功");
	}


}
