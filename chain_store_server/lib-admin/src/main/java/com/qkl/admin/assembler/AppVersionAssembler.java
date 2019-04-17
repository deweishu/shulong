package com.qkl.admin.assembler;


import com.qkl.admin.dto.AppVersionDto;
import com.qkl.admin.entity.AppVersion;
import com.qkl.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class AppVersionAssembler {

	public static AppVersion convertToEntity(AppVersionDto appVersionDto, AppVersion appVersion){
		if(appVersion==null){
			appVersion = new AppVersion();
		}
		appVersion.setId(appVersionDto.getId());
		appVersion.setVersionCode(appVersionDto.getVersionCode());
		appVersion.setVersionName(appVersionDto.getVersionName());
		appVersion.setVersionDesc(appVersionDto.getVersionDesc());
		if(StringUtil.isNotNil(appVersionDto.getDownloadUrl())){
			appVersion.setDownloadUrl(appVersionDto.getDownloadUrl());
		}
		if(StringUtil.isNotNil(appVersionDto.getMd5())){
			appVersion.setMd5(appVersionDto.getMd5());
		}
		if(StringUtil.isNotNil(appVersionDto.getVersionSize())){
			appVersion.setVersionSize(appVersionDto.getVersionSize());
		}
		appVersion.setVersionStatus(appVersionDto.getVersionStatus());
		appVersion.setForceUpdate(appVersionDto.getForceUpdate());
		appVersion.setClientType(appVersionDto.getClientType());
		return appVersion;
	}


	public static AppVersionDto convertToDto(AppVersion appVersion){
		if(appVersion==null){
			return null;
		}
		AppVersionDto appVersionDto = new AppVersionDto();
		appVersionDto.setId(appVersion.getId());
		appVersionDto.setVersionCode(appVersion.getVersionCode());
		appVersionDto.setVersionName(appVersion.getVersionName());
		appVersionDto.setVersionDesc(appVersion.getVersionDesc());
		appVersionDto.setDownloadUrl(appVersion.getDownloadUrl());
		appVersionDto.setMd5(appVersion.getMd5());
		appVersionDto.setVersionStatus(appVersion.getVersionStatus());
		appVersionDto.setVersionSize(appVersion.getVersionSize());
		appVersionDto.setForceUpdate(appVersion.getForceUpdate());
		appVersionDto.setCreateTime(appVersion.getCreateTime());
		appVersionDto.setUpdateTime(appVersion.getUpdateTime());
		appVersionDto.setClientType(appVersion.getClientType());
		return appVersionDto;
	}


	public static List<AppVersionDto> convertToDtoList(List<AppVersion> appVersionList){
		List<AppVersionDto> appVersionDtoList= new ArrayList<>();
		appVersionList.forEach(appVersion -> appVersionDtoList.add(convertToDto(appVersion)));
		return appVersionDtoList;
	}
}
