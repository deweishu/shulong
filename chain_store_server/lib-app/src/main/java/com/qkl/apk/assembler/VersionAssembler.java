package com.qkl.apk.assembler;


import com.qkl.apk.bean.ApkType;
import com.qkl.apk.bean.ApkVersionStatus;
import com.qkl.apk.bean.LinkType;
import com.qkl.apk.dto.VersionDto;
import com.qkl.apk.entity.Version;
import com.qkl.common.bean.JpaProperty;

import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class VersionAssembler {

	public static Version convertToEntity(VersionDto versionDto, Version version){
		if(version==null){
			version = new Version();
		}
		version.setId(versionDto.getId());
		version.setVersionCode(versionDto.getVersionCode());
		version.setVersionName(versionDto.getVersionName());
		version.setVersionContent(versionDto.getVersionContent());
		version.setStatus(versionDto.getStatus()==null?ApkVersionStatus.WAIT_SHENHE: JpaProperty.getProperty(ApkVersionStatus.class,versionDto.getStatus()));
		version.setApkType(versionDto.getApkType()==null?null:JpaProperty.getProperty(ApkType.class,versionDto.getApkType()));
		version.setDownUrl(versionDto.getDownUrl());
		version.setLinkType(versionDto.getLinkType()==null?null:JpaProperty.getProperty(LinkType.class,versionDto.getLinkType()));
		version.setStatusReason(versionDto.getStatusReason());
		version.setSize(versionDto.getSize());
		return version;
	}


	public static VersionDto convertToDto(Version version){
		if(version==null){
			return null;
		}
		VersionDto versionDto = new VersionDto();
		versionDto.setId(version.getId());
		versionDto.setApkId(version.getApk()==null?null:version.getApk().getId());
		versionDto.setApkName(version.getApk()==null?null:version.getApk().getName());
		versionDto.setVersionCode(version.getVersionCode());
		versionDto.setVersionName(version.getVersionName());
		versionDto.setVersionContent(version.getVersionContent());
		versionDto.setStatus(version.getStatus()==null?null:version.getStatus().getCode());
		versionDto.setDownUrl(version.getDownUrl());
		versionDto.setLinkType(version.getLinkType()==null?null:version.getLinkType().getCode());
		versionDto.setLinkTypeDesc(version.getLinkType()==null?null:version.getLinkType().getName());
		versionDto.setStatusReason(version.getStatusReason());
		versionDto.setSize(version.getSize());
		versionDto.setApkType(version.getApkType()==null?null:version.getApkType().getCode());
		versionDto.setCreateTime(version.getCreateTime());
		versionDto.setUpdateTime(version.getUpdateTime());
		versionDto.setpListFile(version.getApk()==null?null:version.getApk().getPlistFile());
		return versionDto;
	}


	public static List<VersionDto> convertToDtoList(List<Version> versionList){
		List<VersionDto> versionDtoList= new ArrayList<>();
		versionList.forEach(version -> versionDtoList.add(convertToDto(version)));
		return versionDtoList;
	}
}
