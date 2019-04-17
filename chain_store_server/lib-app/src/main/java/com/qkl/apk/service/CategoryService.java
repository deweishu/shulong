package com.qkl.apk.service;


import com.qkl.apk.assembler.ApkAssembler;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.bean.CategoryType;
import com.qkl.apk.cache.AppIndexCache;
import com.qkl.apk.cache.CateZtCache;
import com.qkl.apk.cache.CategoryCache;
import com.qkl.apk.cache.TodayAppCache;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.CategoryDto;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.apk.jpa.CategoryRepository;
import com.qkl.apk.dto.CategoryReq;
import com.qkl.apk.assembler.CategoryAssembler;
import com.qkl.apk.entity.Category;
import com.qkl.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.*;
import com.google.common.collect.Lists;
import com.qkl.common.util.StringUtil;
import javax.persistence.criteria.*;



/**
*
generate by dengjihai
*/
@Service
public class CategoryService {

	private Logger logger= LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	CategoryCache categoryCache;

	@Autowired
	ApkRepository apkRepository;

	@Autowired
	CateZtCache cateZtCache;


	@Autowired
	TodayAppCache todayAppCache;

	@Autowired
	AppIndexCache appIndexCache;


	@Transactional(rollbackFor = Exception.class)
	public String save(CategoryDto categoryDto){
		Category  category;
		if (StringUtil.isNotNil(categoryDto.getId())) {
			category=categoryRepository.findOne(categoryDto.getId());
			Assert.notNull(category, "不存该数据");
			category = CategoryAssembler.convertToEntity(categoryDto, category);
		}else{
			category =CategoryAssembler.convertToEntity(categoryDto,null);
		}
		categoryCache.deleteCategory();
		return categoryRepository.save(category).getId();
	}


	public CategoryDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return CategoryAssembler.convertToDto(categoryRepository.findOne(id));
	}


	public Page<CategoryDto> findPage(CategoryReq categoryReq) {
		PageRequest pageRequest = new PageRequest(categoryReq.getPageNumber(), categoryReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Category> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if (StringUtil.isNotBlank(categoryReq.getQueryLike())) {
				predicates.add(builder.equal(root.get("name"), categoryReq.getQueryLike()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Category> categoryPage = categoryRepository.findAll(spec,pageRequest);
		Page<CategoryDto> categoryDtoPage = new PageImpl<>(CategoryAssembler.convertToDtoList(categoryPage.getContent()), pageRequest,categoryPage.getTotalElements());
		return categoryDtoPage;
	}




	/**
	 * 将apk应用添加到专题
	 * @param apkIds
	 * @param cateId
	 */
	public void addApkToCate(String apkIds,String cateId,String ztDate){
		String ids[]=apkIds.split(",");
		List<String> stringList = new ArrayList<>();
		for (String s : ids) {
			stringList.add(s);
		}
		List<Apk> apkList=apkRepository.findByIdIn(stringList);
		//如果是加入到今日主题的，那就不到分类里面去了，就直接在缓存里面
		if(cateId.equals("11")){
			String date= ztDate==null? TimeUtil.dateFormat(TimeUtil.now()):ztDate;
			List<ApkDto> apkDtoList = ApkAssembler.convertToDtoList(apkList);
			todayAppCache.saveTodayApp(date,apkDtoList);
			logger.info("\n ### 添加到日期主题#### {}",date);
		}else if(cateId.equals("-1")){
			//加入到首页模块的APP
			String date= ztDate==null? TimeUtil.dateFormat(TimeUtil.now()):ztDate;
			List<ApkDto> apkDtoList = ApkAssembler.convertToDtoList(apkList);
			appIndexCache.saveTodayApp(date,apkDtoList);
			logger.info("\n ### 添加到首页模块 #### {}",date);
		}else{
			Category  category=categoryRepository.findOne(cateId);
			Assert.notNull(category,"不存在该专题");
			Set<Apk> apkSet =category.getApkSet();
			if(apkSet==null){
				apkSet=new HashSet<>();
			}
			apkSet.addAll(apkList);
			category.setApkSet(apkSet);
			categoryRepository.save(category);
			//清除缓存
			cateZtCache.delZtCache(cateId);
		}


	}

	/**
	 * 从apk所在的应用，删除apk应用
	 * @param apkId
	 * @param cateId
	 */
	public void deleteApkToCate(String apkId,String cateId){
		Category category=categoryRepository.findOne(cateId);
		Set<Apk> apkSet=category.getApkSet();
		Iterator<Apk> apkIterator=apkSet.iterator();
		while (apkIterator.hasNext()){
			Apk apk=apkIterator.next();
			if(apk.getId().equals(apkId)){
				//删除，
				apkIterator.remove();
			}
		}
		category.setApkSet(apkSet);
		categoryRepository.save(category);
		//清除缓存
		cateZtCache.delApk(cateId,apkId);
	}

	//清除缓存
	public void clearCateZtCache(String ztId){
		cateZtCache.delZtCache(ztId);
	}

	/**
	 * 通过专题获取apklist
	 * @param ztId
	 * @return
	 */
	public  List<ApkDto> getApkListByZt(String ztId,Boolean all){
		List<ApkDto> apkDtoList=cateZtCache.getApkListByZt(ztId);
		if(apkDtoList!=null){
			Comparator<ApkDto> comparator = Comparator.comparing(ApkDto::getUpdateTime);
			apkDtoList.sort(comparator.reversed());
		}
		if(apkDtoList!=null && apkDtoList.size()>0){
			return apkDtoList;
		}
		Category category=categoryRepository.findOne(ztId);
		Set<Apk> apkSet=category.getApkSet();
		List<Apk> apkList = new ArrayList<>();
		if(!all){
			Iterator<Apk> apkIterator=apkSet.iterator();
			while (apkIterator.hasNext()){
				Apk apk=apkIterator.next();
				if(apk.getStatus().equals(ApkStatus.NORMAL)){
					apkList.add(apk);
				}
			}
		}else{
			apkList.addAll(apkSet);
		}

		List<ApkDto> apkDtos =ApkAssembler.convertToDtoList(apkList);
		//保存最新的数据到缓存中
		cateZtCache.save(ztId,apkDtos);
		return apkDtos;

	}


	/**
	 * 删除分类信息
	 * @param id
	 */
	public void delete(String id){
		Category category=categoryRepository.findOne(id);
		Assert.notNull(category,"该分类信息不存在");
		List<Apk> apkList=apkRepository.findByCategory_Id(category.getId());
		Assert.isTrue(apkList.size()==0,"该分类下还有APP，不能删除");
		categoryRepository.delete(category);
	}


	/**
	 * 获取分类信息
	 * @return
	 */
	public List<CategoryDto> getCategoryList(){
		List<CategoryDto> categoryDtoList=categoryCache.getCategoryList();
		if(categoryDtoList!=null){
			return categoryDtoList;
		}
		List<Category> categoryList=categoryRepository.findByTypeOrderByCateSortAsc(CategoryType.FENLEI);
		List<CategoryDto> categoryDtos=CategoryAssembler.convertToDtoList(categoryList);
		categoryCache.saveCate(categoryDtos);
		return categoryDtos;
	}
}
