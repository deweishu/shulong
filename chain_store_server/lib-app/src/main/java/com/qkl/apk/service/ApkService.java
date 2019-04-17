package com.qkl.apk.service;


import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.entity.Customer;
import com.qkl.admin.jpa.CustomerRepository;
import com.qkl.admin.service.ConfigService;
import com.qkl.apk.bean.ApkResource;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.dto.*;
import com.qkl.apk.entity.Activity;
import com.qkl.apk.entity.Category;
import com.qkl.apk.entity.Version;
import com.qkl.apk.es.EsApk;
import com.qkl.apk.es.EsApkOperate;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.apk.assembler.ApkAssembler;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.jpa.CategoryRepository;
import com.qkl.apk.jpa.VersionRepository;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.Pinyin4j;
import com.qkl.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Lists;
import com.qkl.common.util.StringUtil;
import javax.persistence.criteria.*;



/**
*
generate by dengjihai
*/
@Service
public class ApkService {

	private Logger logger = LoggerFactory.getLogger(ApkService.class);

	@Autowired
	private ApkRepository apkRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ConfigService configService;

	@Autowired
	EsApkOperate esApkOperate;

	@Autowired
	VersionService versionService;

	@Autowired
	VersionRepository versionRepository;


	@Transactional(rollbackFor = Exception.class)
	public String save(ApkDto apkDto){
		Apk  apk;
		if (StringUtil.isNotNil(apkDto.getId())) {
			apk=apkRepository.findOne(apkDto.getId());
			Assert.notNull(apk, "不存该数据");
			apk = ApkAssembler.convertToEntity(apkDto, apk);
		}else{
			apk =ApkAssembler.convertToEntity(apkDto,null);
            apk.setScoreNum("5");//新添加的应用，都是5分
			if(StringUtil.isNotNil(apkDto.getCustomerId()) && !apk.getCustomerId().equals(CodeConstant.SYS_CUSTOMER_ID) ){
				Boolean flag=validateAddApk(apkDto.getCustomerId());
				Assert.isTrue(flag,"超过创建应用个数");
				Customer customer=customerRepository.findOne(apkDto.getCustomerId());
				Assert.notNull(customer,"不存在该合作商");
				apk.setCustomerId(customer.getId());
				apk.setApkResource(ApkResource.CUSTOMER_ADD);
			}else{
				apk.setApkResource(ApkResource.SYS_ADD);
			}
		}
		Category category=categoryRepository.findOne(apkDto.getCategoryId());
		Assert.notNull(category,"该分类信息不存在");
		apk.setCategory(category);


		String id=apkRepository.save(apk).getId();
		asyncToEs(apk,null);
		return id;
	}


	/**
	 * 判断客户是还能进行添加apk（从配置里面获取值）
	 * @param customerId
	 * @return
	 */
	public Boolean validateAddApk(String customerId){
		ConfigDto configDto=configService.getConfigByKey(SysConfigKey.MAX_APP_NUM.getCode());

		Integer appNum=apkRepository.countByCustomerId(customerId);

		if(appNum>=configDto.getConfigNum()){
			return false;
		}
		return true;
	}

	/**
	 * 更新应用评分
	 * @param apkId
	 * @param scoreNum
	 */
	public void updateApkScoreNum(String apkId,String scoreNum){
		Apk apk=apkRepository.findOne(apkId);
		Assert.notNull(apk,"不存在该应用信息");
		apk.setScoreNum(scoreNum);
		if(StringUtil.isNil(apk.getScoreNum()) || !scoreNum.equals(apk.getScoreNum())){
			apkRepository.save(apk);
		}
	}

	public void selfAsycApkToEs(ApkStatus apkStatus){
		List<Apk> apkList=apkRepository.findByStatus(apkStatus);
		apkList.forEach(apk -> {
			asyncToEs(apk,null);
		});
	}


	public ApkDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return ApkAssembler.convertToDto(apkRepository.findOne(id));
	}


	/**
	 * 通过包名来获取apk信息
	 * @param packageName
	 * @return
	 */
	public ApkDto findByPackageName(String packageName){
		return ApkAssembler.convertToDto(apkRepository.findByPackageNameAndStatus(packageName,ApkStatus.NORMAL));
	}



	/**
	 * 修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	public String updateStatus(Integer status,String id,String statusTxt) throws Exception {
		Apk apk=apkRepository.findOne(id);
		Assert.notNull(apk,"不存在该应用信息");
		apk.setStatus(JpaProperty.getProperty(ApkStatus.class,status));
		if(StringUtil.isNotNil(statusTxt)){
			apk.setStatusReason(statusTxt);
		}
		String string=apkRepository.save(apk).getId();
		asyncToEs(apk,null);
		return string;

	}


	/**
	 * 删除apk，需要把对应的版本信息也一起删掉。
	 * @param id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id){
		Apk apk=apkRepository.findOne(id);
		List<Version> versionList=versionRepository.findByApk(apk);
		versionRepository.delete(versionList);
		apkRepository.delete(id);

	}

	/**
	 * 将数据同步到es 搜索引擎中
	 * @param apkId
	 */
	public void asyncApkToEs(String apkId){
		Apk apk=apkRepository.findOne(apkId);
		asyncToEs(apk,null);
	}


	/**
	 * 同步信息到es
	 * @param apk
	 */
	@Async
	public void asyncToEs(Apk apk,ApkSortDto apkSortDto){
		EsApk esApk = new EsApk();
		esApk.setId(apk.getId());
		esApk.setName(apk.getName());
		esApk.setAppDesc(apk.getDescribe());
		esApk.setAppSpecial(apk.getSpecial());
		esApk.setCategoryId(apk.getCategory().getId());
		esApk.setCreateTime(apk.getCreateTime().getTime());
		esApk.setDownNum(apk.getDownNum());
		esApk.setLogo(apk.getLogo());
		esApk.setStatus(apk.getStatus().getCode());
		esApk.setCandyNum(apk.getCandyNum());
		esApk.setUpdateTime(apk.getUpdateTime().getTime());
		esApk.setCreateDate(TimeUtil.dateFormat(apk.getCreateTime()));
		esApk.setHaveAd(apk.getHaveAd());
		esApk.setHavePlugin(apk.getHavePlugin());
		esApk.setHavePeople(apk.getHavePeople());
		esApk.setClientType(apk.getClientType()==null?null:apk.getClientType().getCode());
		esApk.setMainImg(apk.getMainImg());
		esApk.setScoreNum(apk.getScoreNum());
		esApk.setPackageName(apk.getPackageName());
		esApk.setBuildId(apk.getBuildId());
		esApk.setPlistFile(apk.getPlistFile());
		esApk.setNamePinyin(Pinyin4j.converterToSpell(apk.getName()));
		esApk.setCommentCandy(apk.getCommentCandy());
		esApk.setDevName(apk.getDevName());
		esApk.setDevUrl(apk.getDevUrl());
		esApk.setApplyAge(apk.getApplyAge());
		esApk.setCommentCandy(apk.getCommentCandy());
		if(apkSortDto!=null){
			//应用榜
			if(apkSortDto.getSortType().equals(30)){
				esApk.setAppSort(true);
				esApk.setAppSortNum(apkSortDto.getSortNum());
			}else{
				//游戏榜
				esApk.setGameSort(true);
				esApk.setGameSortNum(apkSortDto.getSortNum());
			}
		}else{
			try {
				EsApk apkById=esApkOperate.getApkById(apk.getId());
				if(apkById!=null){
					esApk.setGameSort(apkById.getGameSort());
					esApk.setGameSortNum(apkById.getGameSortNum());
					esApk.setAppSort(apkById.getAppSort());
					esApk.setAppSortNum(apkById.getAppSortNum());
				}
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			esApkOperate.inserOrUpdateApk(esApk);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("\n #### 同步apkinfo 到es 出现错误");
		}
	}


	/**
	 * 移除榜单-从es里面移除
	 * @param type
	 * @param id
	 */
	public void updateEsApk(Integer type,String id) throws ExecutionException, InterruptedException {

		EsApk esApk=esApkOperate.getApkById(id);
		if(esApk!=null){
			if(type.equals(30)){
				esApk.setAppSort(false);
			}else{
				esApk.setGameSort(false);
			}
			try {
				esApkOperate.inserOrUpdateApk(esApk);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("\n #### 同步apkinfo 到es 出现错误");
			}
		}

	}

	public Page<ApkDto> findPage(ApkReq apkReq) {
		PageRequest pageRequest = new PageRequest(apkReq.getPageNumber(), apkReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Apk> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if (apkReq.getApkResource()!=null) {
				predicates.add(builder.equal(root.get("apkResource"), apkReq.getApkResource()));
			}
			if(StringUtil.isNotNil(apkReq.getName())){
//				predicates.add(builder.equal(root.get("name"), apkReq.getName()));
				predicates.add(builder.like(root.get("name"), "%" +apkReq.getName()+ "%", '/'));
			}
			if(StringUtil.isNotNil(apkReq.getCategoryId())){
				predicates.add(builder.equal(root.join("category").get("id").as(String.class),apkReq.getCategoryId()));
			}
			if(apkReq.getHaveAd()!=null){
				predicates.add(builder.equal(root.get("haveAd"), apkReq.getHaveAd()));
			}
			if(apkReq.getHavePlugin()!=null){
				predicates.add(builder.equal(root.get("havePlugin"), apkReq.getHavePlugin()));
			}
			if(StringUtil.isNotNil(apkReq.getCustomerId())){
				predicates.add(builder.equal(root.get("customerId"), apkReq.getCustomerId()));
			}
			if(apkReq.getStatus()!=null){
				predicates.add(builder.equal(root.get("status"), apkReq.getStatus()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Apk> apkPage = apkRepository.findAll(spec,pageRequest);
		Page<ApkDto> apkDtoPage = new PageImpl<>(ApkAssembler.convertToDtoList(apkPage.getContent()), pageRequest,apkPage.getTotalElements());
		return apkDtoPage;
	}

	/**
	 * 将应用加入到榜单（应用，游戏）
	 * @param apkSortDto
	 */
	public void addApkToSort(ApkSortDto apkSortDto){
		String ids[]=apkSortDto.getApkIds().split(",");
		List<String> stringList = new ArrayList<>();
		for (String s : ids) {
			stringList.add(s);
		}
		List<Apk> apkList=apkRepository.findByIdIn(stringList);
		apkList.forEach(apk -> asyncToEs(apk,apkSortDto));


	}


	public List<ApkDto> findByIdIn(String apkIds){
		String ids[]=apkIds.split(",");
		List<String> stringList = new ArrayList<>();
		for (String s : ids) {
			stringList.add(s);
		}
		List<Apk> apkList=apkRepository.findByIdIn(stringList);
		return  ApkAssembler.convertToDtoList(apkList);
	}


	/**
	 * 增加app的下载次数
	 * @param apkId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String addDownNum(String apkId){
		Apk apk=apkRepository.findOne(apkId);
		Assert.notNull(apk,"不存在该应用");
		apk.setDownNum(apk.getDownNum()+1);
		String id=apkRepository.save(apk).getId();
		asyncToEs(apk,null);
		return id;
	}
}
