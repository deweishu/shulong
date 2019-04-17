package com.qkl.admin.service;


import com.google.common.collect.Lists;
import com.qkl.admin.assembler.AppVersionAssembler;
import com.qkl.admin.dto.AppUpdateDto;
import com.qkl.admin.dto.AppVersionDto;
import com.qkl.admin.dto.AppVersionReq;
import com.qkl.admin.entity.AppVersion;
import com.qkl.admin.jpa.AppVersionRepository;
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

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;



/**
*
generate by dengjihai
*/
@Service
public class AppVersionService {

	@Autowired
	private AppVersionRepository appVersionRepository;


	@Transactional(rollbackFor = Exception.class)
	public String save(AppVersionDto appVersionDto){
		AppVersion  appVersion;
		if (StringUtil.isNotNil(appVersionDto.getId())) {
			appVersion=appVersionRepository.findOne(appVersionDto.getId());
			Assert.notNull(appVersion, "不存该数据");
			appVersion = AppVersionAssembler.convertToEntity(appVersionDto, appVersion);
		}else{
			appVersion =AppVersionAssembler.convertToEntity(appVersionDto,null);
		}
		return appVersionRepository.save(appVersion).getId();
	}


	public AppVersionDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return AppVersionAssembler.convertToDto(appVersionRepository.findOne(id));
	}

	/**
	 * 修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	public String updateStatus(Boolean status,String id) throws Exception {
		AppVersion version=appVersionRepository.findOne(id);
		Assert.notNull(version,"不存在该版本记录");
		version.setVersionStatus(status);
		String string=appVersionRepository.save(version).getId();
		return string;
	}

	public void delete(String id){
        appVersionRepository.delete(id);
    }


	public Page<AppVersionDto> findPage(AppVersionReq appVersionReq) {
		PageRequest pageRequest = new PageRequest(appVersionReq.getPageNumber(), appVersionReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<AppVersion> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();

			if (StringUtil.isNotNil(appVersionReq.getVersionCode())) {
				Path<String> username = root.get("versionCode");
				predicates.add(builder.like(username, "%" + appVersionReq.getVersionCode() + "%", '/'));
			}
			if (StringUtil.isNotNil(appVersionReq.getVersionName())) {
				Path<String> username = root.get("versionName");
				predicates.add(builder.like(username, "%" + appVersionReq.getVersionName() + "%", '/'));
			}

			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<AppVersion> appVersionPage = appVersionRepository.findAll(spec,pageRequest);
		Page<AppVersionDto> appVersionDtoPage = new PageImpl<>(AppVersionAssembler.convertToDtoList(appVersionPage.getContent()), pageRequest,appVersionPage.getTotalElements());
		return appVersionDtoPage;
	}


	/**
	 * 获取系统APP最新版本信息
	 * @param clientType
	 * @return
	 */
	public AppVersionDto getLastUpdate(Integer clientType) {
		List<AppVersion> appVersionList = appVersionRepository.findByClientTypeAndVersionStatusOrderByVersionCodeDesc(clientType, true);
		if(appVersionList.size()==0){
			return null;
		}
		return AppVersionAssembler.convertToDto(appVersionList.get(0));
	}


	/**
	 * app 检查更新
	 * @param clientType
	 * @param versionName
	 * @return
	 */
	public AppUpdateDto checkUpdate(Integer clientType,String versionName){
		List<AppVersion> appVersionList=appVersionRepository.findByClientTypeAndVersionStatusOrderByVersionCodeDesc(clientType,true);
		if(appVersionList.size()==0){
			return null;
		}
		AppVersion appVersion=appVersionList.get(0);
		AppUpdateDto appUpdateDto = new AppUpdateDto();
		//如果版本名不一样，那就需要更新
		if(!appVersion.getVersionName().equals(versionName)){
			appUpdateDto.setUpdate("Yes");
		}else{
			//否则就不需要更新
			appUpdateDto.setUpdate("No");
		}
		appUpdateDto.setApk_file_url(appVersion.getDownloadUrl());
		appUpdateDto.setConstraint(appVersion.getForceUpdate());
		appUpdateDto.setNew_md5(appVersion.getMd5());
		appUpdateDto.setNew_version(appVersion.getVersionName());
		appUpdateDto.setTarget_size(appVersion.getVersionSize());
		appUpdateDto.setUpdate_log(appVersion.getVersionDesc());
		return appUpdateDto;
	}
}
