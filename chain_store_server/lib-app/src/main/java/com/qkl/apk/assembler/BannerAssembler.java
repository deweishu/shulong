package com.qkl.apk.assembler;


import com.qkl.apk.bean.BannerLinkType;
import com.qkl.apk.dto.BannerDto;
import com.qkl.apk.entity.Banner;
import com.qkl.common.bean.JpaProperty;

import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class BannerAssembler {

	public static Banner convertToEntity(BannerDto bannerDto, Banner banner){
		if(banner==null){
			banner = new Banner();
			banner.setStatus(true);
		}else{
			banner.setStatus(bannerDto.getStatus());
		}
		banner.setLogo(bannerDto.getLogo());
		banner.setUrl(bannerDto.getUrl());
		banner.setLinkType(bannerDto.getLinkType()==null?null: JpaProperty.getProperty(BannerLinkType.class,bannerDto.getLinkType()));
		return banner;
	}


	public static BannerDto convertToDto(Banner banner){
		if(banner==null){
			return null;
		}
		BannerDto bannerDto = new BannerDto();
		bannerDto.setId(banner.getId());
		bannerDto.setLogo(banner.getLogo());
		bannerDto.setUrl(banner.getUrl());
		bannerDto.setLinkType(banner.getLinkType()==null?null:banner.getLinkType().getCode());
		bannerDto.setApkId(banner.getApk()==null?null:banner.getApk().getId());
		bannerDto.setApkName(banner.getApk()==null?null:banner.getApk().getName());
		bannerDto.setStatus(banner.getStatus());
		bannerDto.setCreateTime(banner.getCreateTime());
		bannerDto.setUpdateTime(banner.getUpdateTime());
		return bannerDto;
	}


	public static List<BannerDto> convertToDtoList(List<Banner> bannerList){
		List<BannerDto> bannerDtoList= new ArrayList<>();
		bannerList.forEach(banner -> bannerDtoList.add(convertToDto(banner)));
		return bannerDtoList;
	}
}
