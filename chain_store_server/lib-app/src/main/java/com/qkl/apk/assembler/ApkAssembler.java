package com.qkl.apk.assembler;


import com.qkl.apk.bean.ApkResource;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.bean.AppType;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.entity.Apk;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class ApkAssembler {

	public static Apk convertToEntity(ApkDto apkDto, Apk apk){
		if(apk==null){
			apk = new Apk();
		}
		apk.setId(apkDto.getId());
		apk.setName(apkDto.getName());
		apk.setLogo(apkDto.getLogo());
        apk.setMainImg(apkDto.getMainImg());
		apk.setClientType(apkDto.getClientType()==null? AppType.ANDROID_APP: JpaProperty.getProperty(AppType.class,apkDto.getClientType()));
		apk.setDescribe(apkDto.getDescribe());
		apk.setHaveAd(apkDto.getHaveAd());
		apk.setHavePlugin(apkDto.getHavePlugin());
		apk.setHavePeople(apkDto.getHavePeople());
		apk.setSpecial(apkDto.getSpecial());
		apk.setCustomerId(apkDto.getCustomerId());
		apk.setApkResource(apkDto.getApkResource()==null? ApkResource.SYS_ADD:JpaProperty.getProperty(ApkResource.class,apkDto.getApkResource()));
		apk.setStatus(apkDto.getStatus()==null? ApkStatus.DAI_SHENHE:JpaProperty.getProperty(ApkStatus.class,apkDto.getStatus()));
		apk.setBuildId(apkDto.getBuildId());
		apk.setPackageName(apkDto.getPackageName());
		apk.setDevName(apkDto.getDevName());
		apk.setDevUrl(apkDto.getDevUrl());
		apk.setLaguage(apkDto.getLaguage());
		apk.setApplyAge(apkDto.getApplyAge());
		if(apkDto.getDownNum()!=null){
			apk.setDownNum(apkDto.getDownNum());
		}
		if(apkDto.getCandyNum()!=null){
			apk.setCandyNum(apkDto.getCandyNum());
		}
		if(StringUtil.isNotNil(apkDto.getStatusReason())){
			apk.setStatusReason(apkDto.getStatusReason());
		}
		if(apkDto.getScoreNum()!=null){
			apk.setScoreNum(apkDto.getScoreNum());
		}
		if(apkDto.getShareCandy()!=null){
			apk.setShareCandy(apkDto.getShareCandy());
		}
		if(apkDto.getShareMaxNum()!=null){
			apk.setShareMaxNum(apkDto.getShareMaxNum());
		}
		if(StringUtil.isNotNil(apkDto.getPlistFile())){
			apk.setPlistFile(apkDto.getPlistFile());
		}
		if(apkDto.getCommentCandy()!=null){
			apk.setCommentCandy(apkDto.getCommentCandy());
		}
		return apk;
	}


	public static ApkDto convertToDto(Apk apk){
		if(apk==null){
			return null;
		}
		ApkDto apkDto = new ApkDto();
		apkDto.setId(apk.getId());
		apkDto.setName(apk.getName());
		apkDto.setLogo(apk.getLogo());
        apkDto.setMainImg(apk.getMainImg());
		apkDto.setClientType(apk.getClientType().getCode());
		apkDto.setDownNum(apk.getDownNum());
		apkDto.setDescribe(apk.getDescribe());
		apkDto.setHaveAd(apk.getHaveAd());
		apkDto.setHavePlugin(apk.getHavePlugin());
		apkDto.setHavePeople(apk.getHavePeople());
		apkDto.setSpecial(apk.getSpecial());
		apkDto.setCategoryId(apk.getCategory().getId());
		apkDto.setCateName(apk.getCategory().getName());
		apkDto.setCustomerId(apk.getCustomerId());
		apkDto.setCandyNum(apk.getCandyNum());
		apkDto.setApkResource(apk.getApkResource().getCode());
		apkDto.setApkResourceTxt(apk.getApkResource().getName());
		apkDto.setStatus(apk.getStatus()==null?null:apk.getStatus().getCode());
		apkDto.setStatusTxt(apk.getStatus()==null?null:apk.getStatus().getName());
		apkDto.setCreateTime(apk.getCreateTime());
		apkDto.setUpdateTime(apk.getUpdateTime());
		apkDto.setStatusReason(apk.getStatusReason());
		apkDto.setScoreNum(apk.getScoreNum());
		apkDto.setBuildId(apk.getBuildId());
		apkDto.setPackageName(apk.getPackageName());
		apkDto.setShareCandy(apk.getShareCandy());
		apkDto.setShareMaxNum(apk.getShareMaxNum());
		apkDto.setPlistFile(apk.getPlistFile());
		apkDto.setCommentCandy(apk.getCommentCandy());
		apkDto.setDevName(apk.getDevName());
		apkDto.setDevUrl(apk.getDevUrl());
		apkDto.setApplyAge(apk.getApplyAge());
		apkDto.setLaguage(apk.getLaguage());
		return apkDto;
	}


	public static List<ApkDto> convertToDtoList(List<Apk> apkList){
		List<ApkDto> apkDtoList= new ArrayList<>();
		apkList.forEach(apk -> apkDtoList.add(convertToDto(apk)));
		return apkDtoList;
	}
}
