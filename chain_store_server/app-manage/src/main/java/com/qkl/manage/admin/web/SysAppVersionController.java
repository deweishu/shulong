package com.qkl.manage.admin.web;


import com.qkl.admin.annotaion.IngoreParam;
import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.dto.AppVersionReq;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.enums.OssDirType;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.Md5CaculateUtil;
import com.qkl.common.util.PlistFileUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.admin.dto.AppVersionDto;
import com.qkl.admin.service.AppVersionService;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
* generate by dengjihai at 2018-09-30
 * 系统版本管理
*/
@Controller
@RequestMapping("/sys/appversion")
public class SysAppVersionController extends BaseController {

	@Autowired
	private AppVersionService appVersionService;

	@Autowired
	OSSManager ossManager;

	@Autowired
	PlistFileUtil plistFileUtil;

	@RequestPermission(value = AdminPermission.APP_MANAGE_VERSION)
	@MenuMapping(value = "APP版本管理", menu = BoardMenu.ADMIN, weight = 12)
	@GetMapping("/index")
	public String index() {
		return "admin/sys_app/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<AppVersionDto> data(AppVersionReq appVersionReq) {
		appVersionReq.setPageSize(getPageRequest().getPageSize());
		appVersionReq.setPageNumber(getPageRequest().getPageNumber());
		Page<AppVersionDto> pageData = appVersionService.findPage(appVersionReq);
		return pageData;
	}

	/**
	 * 更改版本信息状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestPermission(value = AdminPermission.APP_MANAGE_VERSION)
	@SystemLogAnnotation(desc = "修改了系统APP的状态信息")
	@ResponseBody
	@GetMapping("/status/{id}/{status}")
	public JsonResponse updateStatus(@PathVariable String id,@PathVariable Boolean status){
		try {
			appVersionService.updateStatus(status,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WebUtil.successJsonResponse("操作成功");
	}

	@RequestPermission(value = AdminPermission.APP_MANAGE_VERSION)
	@SystemLogAnnotation(desc = "删除了系统APP版本")
	@ResponseBody
	@GetMapping("/delete/{id}")
	public JsonResponse deleteApp(@PathVariable String id){
		try {
			appVersionService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WebUtil.successJsonResponse("操作成功");
	}



	/**
	 * 进入新增页面
	 *
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(Model model,@PathVariable String id) {
		model.addAttribute("appVersion",appVersionService.findOne(id));
		return "admin/sys_app/edit";
	}


	/**
	* 进入新增页面
	*
	*/
	@GetMapping("/add")
	public String toSave(Model model) {
		return "admin/sys_app/edit";
	}
	/**
	* 添加数据方法
	*/
	@SystemLogAnnotation(desc = "发布了系统新版本")
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(@IngoreParam(value = true) @RequestParam("apkFile") MultipartFile apkFile, AppVersionDto appVersionDto) throws IOException {
		if (apkFile != null && !apkFile.isEmpty() && apkFile.getSize() > 0) {
			InputStream is = apkFile.getInputStream();
			InputStream fis = apkFile.getInputStream();
			String fileName = apkFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String tempFileName = StringUtil.getUUID() +"."+ suffix;
			// 上传到OSS上
			String ossUrl = ossManager.upload(OssDirType.APK, Boolean.TRUE, is, tempFileName);
			String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
			appVersionDto.setMd5(md5);
			IOUtils.closeQuietly(fis);
			//如果是ios，
			if(appVersionDto.getClientType().equals(20)){
				String tempPlist = StringUtil.getUUID() +".plist";
				File file=plistFileUtil.createPlist(ossUrl,tempPlist,appVersionDto.getVersionName(),"ChainStore");
				FileInputStream inputStream= new FileInputStream(file);
				String plistFile=ossManager.upload(OssDirType.PLIST,Boolean.FALSE,inputStream,"chain_store.plist");
				appVersionDto.setDownloadUrl(plistFile);
			}else{
				appVersionDto.setDownloadUrl(ossUrl);
			}
			appVersionDto.setVersionSize(StringUtil.getPrintSize(apkFile.getSize()));
		}
		appVersionService.save(appVersionDto);
		return WebUtil.successJsonResponse("保存成功");
	}


}
