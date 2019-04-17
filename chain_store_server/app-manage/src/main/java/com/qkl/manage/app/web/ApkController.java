package com.qkl.manage.app.web;


import com.qkl.admin.annotaion.IngoreParam;
import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.dto.CustomerDto;
import com.qkl.admin.service.CustomerService;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.bean.AppType;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.ApkReq;
import com.qkl.apk.service.ApkService;
import com.qkl.apk.service.CategoryService;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.enums.OssDirType;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.app.dto.ApkStatusReq;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


/**
 * app 应用管理
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/apk")
public class ApkController extends BaseController {

	@Autowired
	private ApkService apkService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	OSSManager ossManager;

	@Autowired
	AdminSingleSession adminSingleSession;

	@Autowired
	CustomerService customerService;


	@GetMapping("/select/page")
	public String selectApkIndex(){
		return "apk/select_list";
	}



	@GetMapping("/select/page/mult")
	public String selectMultApkIndex(){
		return "apk/select_mult_list";
	}



	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.MY_APP_LIST)
	@MenuMapping(value = "我的应用", menu = BoardMenu.APP_MARKET, weight = 2)
	public String index(Model model) {
		if(isPartner()){
			CustomerDto customerDto=customerService.findByPhone(adminSingleSession.getUser().getMobile());
			model.addAttribute("customerId",customerDto.getId());
		}else{
			model.addAttribute("customerId", CodeConstant.SYS_CUSTOMER_ID);
		}
		return "apk/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<ApkDto> data(ApkReq apkReq) {
		apkReq.setPageSize(getPageRequest().getPageSize());
		apkReq.setPageNumber(getPageRequest().getPageNumber());
		Page<ApkDto> pageData = apkService.findPage(apkReq);
		return pageData;
	}


	/**
	 * 进入新增页面
	 *
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(Model model,@PathVariable String id) {
		ApkDto apkDto =apkService.findOne(id);
		model.addAttribute("apk",apkDto);
		model.addAttribute("customerId",apkDto.getCustomerId());
		model.addAttribute("categoryList",categoryService.getCategoryList());
		model.addAttribute("typeList", JpaProperty.getPropertyList(AppType.class));
		model.addAttribute("partner",isPartner());
		return "apk/edit";
	}


	/**
	 * 更改应用状态
	 * @param apkStatusReq
	 * @return
	 */
	@SystemLogAnnotation(desc = "更改了我的应用的状态信息")
	@ResponseBody
	@PostMapping("/status")
	public JsonResponse updateStatus(@RequestBody ApkStatusReq apkStatusReq){
		try {
			Integer statusCode=apkStatusReq.getStatus();
			if(statusCode.equals(ApkStatus.DISABLE.getCode()) || statusCode.equals(ApkStatus.NORMAL.getCode())){
				apkService.updateStatus(apkStatusReq.getStatus(),apkStatusReq.getId(),
						apkStatusReq.getStatusTxt());
			}else{
				return WebUtil.errorJsonResponse("操作状态不正确");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WebUtil.successJsonResponse("操作成功");
	}


	/**
	 * 删除我的应用
	 * @param id
	 * @return
	 */
	@SystemLogAnnotation(desc = "删除了我的应用信息")
	@ResponseBody
	@GetMapping("/del/{id}")
	public JsonResponse delApk(@PathVariable String id){
		try {
			apkService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WebUtil.successJsonResponse("操作成功");
	}


	/**
	 * 校验客户是否还能继续添加app
	 * @return
	 */
	@ResponseBody
	@GetMapping("/valid/add")
	public JsonResponse validateAdd(){
		if(isPartner()){
			CustomerDto customerDto=customerService.findByPhone(adminSingleSession.getUser().getMobile());
			return WebUtil.successJsonResponse("获取校验结果成功",apkService.validateAddApk(customerDto.getId()));
		}else{
			return WebUtil.successJsonResponse("获取校验结果成功",true);
		}
	}


	/**
	* 进入新增页面
	*
	*/
	@GetMapping("/add/{customerId}")
	public String toSave(Model model,@PathVariable String customerId) {
		model.addAttribute("categoryList",categoryService.getCategoryList());
		model.addAttribute("customerId",customerId);
		model.addAttribute("typeList", JpaProperty.getPropertyList(AppType.class));
		model.addAttribute("partner",isPartner());
		return "apk/edit";
	}
	/**
	* 添加数据方法
	*/
	@SystemLogAnnotation(desc = "创建/更新了应用信息")
	@ResponseBody
	@PostMapping("/save")
	public JsonResponse save(@IngoreParam(value = true) @RequestParam("logoFile") MultipartFile logoFile,
							 @IngoreParam(value = true) @RequestParam("mainImgFile") MultipartFile mainImgFile,ApkDto apkDto) throws IOException {
		if (logoFile != null && !logoFile.isEmpty() && logoFile.getSize() > 0) {
			InputStream is = logoFile.getInputStream();
			String fileName = logoFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String tempFileName = StringUtil.getUUID() +"."+ suffix;
			String ossUrl = ossManager.upload(OssDirType.IMG_PATH, Boolean.TRUE, is, tempFileName);
			apkDto.setLogo(ossUrl);
		}
		if (mainImgFile != null && !mainImgFile.isEmpty() && mainImgFile.getSize() > 0) {
			InputStream is = mainImgFile.getInputStream();
			String fileName = mainImgFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String tempFileName = StringUtil.getUUID() +"."+ suffix;
			String ossUrl = ossManager.upload(OssDirType.IMG_PATH, Boolean.TRUE, is, tempFileName);
			apkDto.setMainImg(ossUrl);
		}
		apkDto.setCustomerId(apkDto.getCustomerId());
		apkService.save(apkDto);
		return WebUtil.successJsonResponse("保存成功");
	}


}
