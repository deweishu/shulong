package com.qkl.apk.service;


import com.google.common.collect.Lists;
import com.qkl.apk.assembler.VersionAssembler;
import com.qkl.apk.bean.*;
import com.qkl.apk.cache.VersionCache;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.VersionDto;
import com.qkl.apk.dto.VersionReq;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.entity.Version;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.apk.jpa.VersionRepository;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
@Service
public class VersionService {

	@Autowired
	private VersionRepository versionRepository;

	@Autowired
	ApkRepository apkRepository;


	@Autowired
	VersionCache versionCache;

	@Transactional(rollbackFor = Exception.class)
	public String save(VersionDto versionDto,String plistFile){
		if(StringUtil.isNotNil(plistFile)){
			Apk apk=apkRepository.findOne(versionDto.getApkId());
			Assert.notNull(apk,"不存在该应用");
			apk.setPlistFile(plistFile);
			apkRepository.save(apk);
		}
		return save(versionDto);
	}


	@Transactional(rollbackFor = Exception.class)
	public String save(VersionDto versionDto){
		Version  version;
		if (StringUtil.isNotNil(versionDto.getId())) {
			version=versionRepository.findOne(versionDto.getId());
			Assert.notNull(version, "不存该数据");
			version = VersionAssembler.convertToEntity(versionDto, version);
		}else{
			version =VersionAssembler.convertToEntity(versionDto,null);
		}
		Assert.isTrue(versionDto.getApkId()!=null,"所属应用ID不能为空");
		if(StringUtil.isNotNil(versionDto.getApkId())){
			version.setApk(apkRepository.findOne(versionDto.getApkId()));
		}
		return versionRepository.save(version).getId();
	}


	public VersionDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return VersionAssembler.convertToDto(versionRepository.findOne(id));
	}


	public Page<VersionDto> findPage(VersionReq versionReq) {
		PageRequest pageRequest = new PageRequest(versionReq.getPageNumber(), versionReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Version> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if(StringUtil.isNotNil(versionReq.getApkId())){
				predicates.add(builder.equal(root.join("apk").get("id").as(String.class),versionReq.getApkId()));
			}
			if(StringUtil.isNotNil(versionReq.getQueryLike())){
				predicates.add(builder.like(root.join("apk").get("name").as(String.class),"%" +versionReq.getQueryLike()+ "%", '/'));
			}
			if(versionReq.getAudit()){
				predicates.add(builder.equal(root.get("status"), ApkVersionStatus.WAIT_SHENHE));
				query.orderBy(builder.asc(root.get("status")));
			}else{
				query.orderBy(builder.desc(root.get("createTime")));
			}
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Version> versionPage = versionRepository.findAll(spec,pageRequest);
		Page<VersionDto> versionDtoPage = new PageImpl<>(VersionAssembler.convertToDtoList(versionPage.getContent()), pageRequest,versionPage.getTotalElements());
		return versionDtoPage;
	}

	/**
	 * 修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	public String updateStatus(Integer status,String id) throws Exception {
		Version version=versionRepository.findOne(id);
		Assert.notNull(version,"不存在该版本记录");
		version.setStatus(JpaProperty.getProperty(ApkVersionStatus.class,status));
		String string=versionRepository.save(version).getId();
		versionCache.delete(string);
		return string;

	}

	/**
	 * 修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	public String updateStatus(Integer status,String id,String statusTxt) throws Exception {
		Version version=versionRepository.findOne(id);
		Assert.notNull(version,"不存在该版本记录");
		version.setStatus(JpaProperty.getProperty(ApkVersionStatus.class,status));
		if(StringUtil.isNotNil(statusTxt)){
			version.setStatusReason(statusTxt);
		}
		String string=versionRepository.save(version).getId();
		versionCache.delete(string);
		return string;

	}

	/**
	 * 删除应用信息
	 * @param id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id){
		Version version=versionRepository.findOne(id);
		versionCache.delete(version.getId());
		versionRepository.delete(version);
	}


	/**
	 * 获取最新的版本信息（通过apkid）
	 * @param apkId
	 * @param appType
	 * @return
	 */
	public VersionDto getLastVersion(String apkId,AppType appType){
		Apk apk = apkRepository.findOne(apkId);
		Assert.notNull(apk,"不存在该应用信息");
		List<Version> versionList;
		if(appType==null){
			versionList=versionRepository.findByApk_IdAndStatusOrderByVersionCodeDesc(apkId,ApkVersionStatus.SHANGJIA);
		}else{
			ApkType apkType=JpaProperty.getProperty(ApkType.class,appType.getCode());
			versionList=versionRepository.findByApk_IdAndApkTypeAndStatusOrderByVersionCodeDesc(apkId,apkType,ApkVersionStatus.SHANGJIA);
		}
		if(versionList.size()>0){
			Version version=versionList.get(0);
			//如果是ios，并且连接是自有安装包 plist file 不为空
			if(appType!=null && appType.getCode().equals(AppType.IOS_APP.getCode()) && version.getLinkType().equals(LinkType.SELF_LINK) &&
					StringUtil.isNotNil(apk.getPlistFile())){
				version.setDownUrl(apk.getPlistFile());
			}
			return VersionAssembler.convertToDto(version);
		}
		return null;
	}
}
