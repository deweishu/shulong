package com.qkl.apk.assembler;


import com.qkl.apk.bean.AcitivityType;
import com.qkl.apk.dto.ActivityDto;
import com.qkl.apk.entity.Activity;
import com.qkl.common.bean.JpaProperty;

import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class ActivityAssembler {

	public static Activity convertToEntity(ActivityDto activityDto, Activity activity){
		if(activity==null){
			activity = new Activity();
			activity.setStatus(true);
		}else{
			activity.setStatus(activityDto.getStatus());
		}
		activity.setId(activityDto.getId());
		activity.setMainImg(activityDto.getMainImg());
		activity.setLogo(activityDto.getLogo());
		activity.setTitle(activityDto.getTitle());
		activity.setLinkUrl(activityDto.getLinkUrl());
		activity.setType(activityDto.getType()==null?null: JpaProperty.getProperty(AcitivityType.class,activityDto.getType()));
		activity.setStatus(activityDto.getStatus());
		activity.setRule(activityDto.getRule());
		activity.setInfomation(activityDto.getInfomation());
		return activity;
	}


	public static ActivityDto convertToDto(Activity activity){
		if(activity==null){
			return null;
		}
		ActivityDto activityDto = new ActivityDto();
		activityDto.setId(activity.getId());
		activityDto.setMainImg(activity.getMainImg());
		activityDto.setLogo(activity.getLogo());
		activityDto.setTitle(activity.getTitle());
		activityDto.setLinkUrl(activity.getLinkUrl());
		activityDto.setStatus(activity.getStatus());
		activityDto.setCreateTime(activity.getCreateTime());
		activityDto.setUpdateTime(activity.getUpdateTime());
		activityDto.setType(activity.getType()==null?null:activity.getType().getCode());
		activityDto.setRule(activity.getRule());
		activityDto.setInfomation(activity.getInfomation());
		return activityDto;
	}


	public static List<ActivityDto> convertToDtoList(List<Activity> activityList){
		List<ActivityDto> activityDtoList= new ArrayList<>();
		activityList.forEach(activity -> activityDtoList.add(convertToDto(activity)));
		return activityDtoList;
	}
}
