package com.qkl.manage.app.web;


import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.dto.ApkSearchReq;
import com.qkl.apk.es.EsApkOperate;
import com.qkl.apk.service.ApkService;
import com.qkl.common.dto.PageResponse;
import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.apk.dto.CategoryReq;
import com.qkl.apk.dto.CategoryDto;
import com.qkl.apk.service.CategoryService;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


/**
 * 行情分类
* generate by dengjihai at 2018-08-24
*/
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;


	@Autowired
	EsApkOperate esApkOperate;

	@Autowired
	ApkService apkService;

	@GetMapping("/sort/app")
	@RequestPermission(value = AdminPermission.APP_SORT)
	@MenuMapping(value = "应用榜单", menu = BoardMenu.APP_MARKET, weight = 1)
	public String appSort() {
		return "catgory/app_sort_list";
	}


	@GetMapping("/sort/game")
	@RequestPermission(value = AdminPermission.GAME_SORT)
	@MenuMapping(value = "游戏榜单", menu = BoardMenu.APP_MARKET, weight = 1)
	public String gameSort() {
		return "catgory/game_sort_list";
	}


	/**
	 * 分页请求数据
	 */
	@ResponseBody
	@PostMapping("/sort/data/{type}")
	public PageResponse sortData(@PathVariable Integer type) {
		ApkSearchReq apkSearchReq = new ApkSearchReq();
		apkSearchReq.setSortType(type);
		apkSearchReq.setPageNo(getPageRequest().getPageNumber()+1);
		return esApkOperate.getApkList(apkSearchReq);
	}

	/**
	 * 移除应用/游戏榜单
	 * @param id
	 * @return
	 */
	@SystemLogAnnotation(desc = "移除了榜单APP")
	@GetMapping("/sort/delete/{type}/{id}")
	@ResponseBody
	public JsonResponse removeSort(@PathVariable Integer type,@PathVariable String id) throws ExecutionException, InterruptedException {
		apkService.updateEsApk(type,id);
		return WebUtil.successJsonResponse("操作成功");
	}


	@GetMapping("/index")
	@RequestPermission(value = AdminPermission.APP_CATEGORY_LIST)
	@MenuMapping(value = "分类专题", menu = BoardMenu.APP_MARKET, weight = 1)
	public String index() {
		return "catgory/list";
	}


	/**
	* 分页请求数据
	*/
	@ResponseBody
	@PostMapping("/data")
	public Page<CategoryDto> data(@RequestParam(value = "searchText", required = false) String searchText) { 
		CategoryReq categoryReq = new CategoryReq();
		categoryReq.setQueryLike(searchText);
		categoryReq.setPageSize(getPageRequest().getPageSize());
		categoryReq.setPageNumber(getPageRequest().getPageNumber());
		Page<CategoryDto> pageData = categoryService.findPage(categoryReq);
		return pageData;
	}


	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping("/delete/{id}")
	@SystemLogAnnotation(desc = "删除了分类信息")
	public JsonResponse deleteCategory(@PathVariable String id){
		categoryService.delete(id);
		return WebUtil.successJsonResponse("删除分类信息成功");
	}


	/**
	 * 进入编辑页面
	 *
	 */
	@GetMapping("/edit/{id}")
	public String toEdit(Model model,@PathVariable String id) {
		model.addAttribute("category",categoryService.findOne(id));
		return "catgory/edit";
	}


	/**
	* 进入新增页面
	*
	*/
	@GetMapping("/add")
	public String toSave(Model model) {
		return "catgory/edit";
	}
	/**
	* 添加数据方法
	*/
	@ResponseBody
	@SystemLogAnnotation(desc = "增加了分类信息")
	@PostMapping("/save")
	public JsonResponse save(CategoryDto categoryDto) {
		categoryService.save(categoryDto);
		return WebUtil.successJsonResponse("保存成功");
	}




}
