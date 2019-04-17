package com.qkl.apk.service;


import com.google.common.collect.Lists;
import com.qkl.apk.assembler.ActivityAssembler;
import com.qkl.apk.dto.ActivityDto;
import com.qkl.apk.dto.ActivityReq;
import com.qkl.apk.entity.Activity;
import com.qkl.apk.es.EsActivityInfo;
import com.qkl.apk.es.EsActivityOperate;
import com.qkl.apk.jpa.ActivityRepository;
import com.qkl.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.List;



/**
*
generate by dengjihai
*/
@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	EsActivityOperate esActivityOperate;


	@Transactional(rollbackFor = Exception.class)
	public String save(ActivityDto activityDto) throws Exception {
		Activity  activity;
		if (StringUtil.isNotNil(activityDto.getId())) {
			activity=activityRepository.findOne(activityDto.getId());
			Assert.notNull(activity, "不存该数据");
			activity = ActivityAssembler.convertToEntity(activityDto, activity);
		}else{
			activity =ActivityAssembler.convertToEntity(activityDto,null);
		}
		String id=activityRepository.save(activity).getId();
		asyncToEs(activity);
		return id;
	}


	public ActivityDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return ActivityAssembler.convertToDto(activityRepository.findOne(id));
	}


	/**
	 * 修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	public String updateStatus(Boolean status,String id) throws Exception {
		Activity activity=activityRepository.findOne(id);
		Assert.notNull(activity,"不存在该活动信息");
		activity.setStatus(status);
		String string=activityRepository.save(activity).getId();
		asyncToEs(activity);
		return string;

	}





	public Page<ActivityDto> findPage(ActivityReq activityReq) {
		PageRequest pageRequest = new PageRequest(activityReq.getPageNumber(), activityReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Activity> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();

			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Activity> activityPage = activityRepository.findAll(spec,pageRequest);
		Page<ActivityDto> activityDtoPage = new PageImpl<>(ActivityAssembler.convertToDtoList(activityPage.getContent()), pageRequest,activityPage.getTotalElements());
		return activityDtoPage;
	}

	@Async
	public void asyncToEs(Activity activity) throws Exception {
		EsActivityInfo esActivityInfo = new EsActivityInfo();
		esActivityInfo.setId(activity.getId());
		esActivityInfo.setStatus(activity.getStatus());
		esActivityInfo.setTitle(activity.getTitle());
		esActivityInfo.setInfomation(activity.getInfomation());
		esActivityInfo.setLinkUrl(activity.getLinkUrl());
		esActivityInfo.setLogo(activity.getLogo());
		esActivityInfo.setMainImg(activity.getMainImg());
		esActivityInfo.setRule(activity.getRule());
		esActivityInfo.setType(activity.getType().getCode());
		esActivityInfo.setCreateDate(activity.getCreateTime().getTime());
		esActivityOperate.inserOrUpdateActivity(esActivityInfo);
	}
}
