package com.qkl.manage.app.web;


import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.bean.ApkType;
import com.qkl.apk.bean.LinkType;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.VersionDto;
import com.qkl.apk.dto.VersionReq;
import com.qkl.apk.service.ApkService;
import com.qkl.apk.service.VersionService;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.enums.OssDirType;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.*;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;


/**
 * apk - version
* generate by dengjihai at 2018-08-29
*/
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

	@Autowired
	private VersionService versionService;

	@Autowired
	OSSManager ossManager;

	@Autowired
	ApkService apkService;

	@Autowired
	PlistFileUtil plistFileUtil;

	@RequestPermission(value = AdminPermission.APP_AUDIT)
	@MenuMapping(value = "APP版本审核", menu = BoardMenu.APP_MARKET, weight = 10)
	@GetMapping("/audti/app")
	public String auditAppList() {
		return "version/audit_list";
	}



	@RequestPermission(value = AdminPermission.APP_VERSION)
	@GetMapping("/index/{apkId}")
	public String index(@PathVariable String apkId,Model model) {
		model.addAttribute("apkId",apkId);
		return "version/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<VersionDto> data(VersionReq versionReq,@RequestParam(value = "searchText", required = false) String searchText) {
		versionReq.setQueryLike(searchText);
		versionReq.setPageSize(getPageRequest().getPageSize());
		versionReq.setPageNumber(getPageRequest().getPageNumber());
		Page<VersionDto> pageData = versionService.findPage(versionReq);
		return pageData;
	}



	/**
	* 进入新增页面
	*
	*/
	@RequestPermission(value = AdminPermission.APP_VERSION_UPDATE)
	@GetMapping("/add/{apkId}")
	public String toSave(Model model,@PathVariable String apkId) {
		model.addAttribute("apkId",apkId);
		model.addAttribute("typeList", JpaProperty.getPropertyList(LinkType.class));
		model.addAttribute("apkTypeList", JpaProperty.getPropertyList(ApkType.class));
		model.addAttribute("apkPath","apk/" + TimeUtil.getDateSerialStr());
		return "version/edit";
	}


	/**
	 * 更改版本信息状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestPermission(value = AdminPermission.APP_VERSION_UPDATE)
	@SystemLogAnnotation(desc = "修改了APP版本状态")
	@ResponseBody
	@GetMapping("/status/{id}/{status}")
	public JsonResponse updateStatus(@PathVariable String id,@PathVariable Integer status){
		try {
			versionService.updateStatus(status,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WebUtil.successJsonResponse("操作成功");
	}

	@RequestPermission(value = AdminPermission.APP_VERSION_DELETE)
	@SystemLogAnnotation(desc = "删除了版本操作")
	@ResponseBody
	@GetMapping("/delete/{id}")
	public JsonResponse delete(@PathVariable String id){
		try {
			versionService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WebUtil.successJsonResponse("操作成功");
	}




	/**
	* 添加数据方法
	*/
	@SystemLogAnnotation(desc = "发布/更新了APP版本信息")
	@RequestPermission(value = AdminPermission.APP_VERSION_UPDATE)
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(
							 VersionDto versionDto) throws IOException {
//		//logo
//		if (apkFile != null && !apkFile.isEmpty() && apkFile.getSize() > 0) {
//			InputStream is = apkFile.getInputStream();
//			String fileName = apkFile.getOriginalFilename();
//			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
//			String tempFileName = StringUtil.getUUID() +"."+ suffix;
//			// 上传到OSS上
//			String ossUrl = ossManager.upload(OssDirType.APK, Boolean.TRUE, is, tempFileName);
//			versionDto.setDownUrl(ossUrl);
//			versionDto.setSize(StringUtil.getPrintSize(apkFile.getSize()));
//		}
		logger.info("\n 上传apk版本，接收到数据：{}", JsonUtil.beanToJson(versionDto));
		if(StringUtil.isNotNil(versionDto.getSize())){
			versionDto.setSize(versionDto.getSize().toUpperCase());
		}
		//如果是ios，那就需要生成一个plist文件，
		if(versionDto.getApkType().equals(ApkType.IOS_APK.getCode())){
			String tempFileName = StringUtil.getUUID() +".plist";
			ApkDto apkDto =apkService.findOne(versionDto.getApkId());
			File file=plistFileUtil.createPlist(versionDto.getDownUrl(),tempFileName,versionDto.getVersionName(),apkDto.getName());
			String pListFile=ossManager.upload(OssDirType.PLIST,Boolean.TRUE,file,tempFileName);
			versionService.save(versionDto,pListFile);
		}else{
			versionService.save(versionDto);
		}
		return WebUtil.successJsonResponse("保存成功");
	}


}
