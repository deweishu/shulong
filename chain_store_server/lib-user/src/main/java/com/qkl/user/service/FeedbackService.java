package com.qkl.user.service;


import com.qkl.user.dto.FeedbackDto;
import com.qkl.user.entity.User;
import com.qkl.user.jpa.FeedbackRepository;
import com.qkl.user.dto.FeedbackReq;
import com.qkl.user.assembler.FeedbackAssembler;
import com.qkl.user.entity.Feedback;
import com.qkl.user.jpa.UserRepository;
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
//反馈业务
@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	UserRepository userRepository;


	@Transactional(rollbackFor = Exception.class)
	public String save(FeedbackDto feedbackDto){
		Feedback  feedback;
		if (StringUtil.isNotNil(feedbackDto.getId())) {
			feedback=feedbackRepository.findOne(feedbackDto.getId());
			Assert.notNull(feedback, "不存该数据");
			feedback = FeedbackAssembler.convertToEntity(feedbackDto, feedback);
		}else{
			feedback =FeedbackAssembler.convertToEntity(feedbackDto,null);
		}
		User user=userRepository.findOne(feedbackDto.getUserId());
		Assert.notNull(user,"不存在该用户信息");
		feedback.setUser(user);
		return feedbackRepository.save(feedback).getId();
	}


	public FeedbackDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return FeedbackAssembler.convertToDto(feedbackRepository.findOne(id));
	}


	public void delete(String id){
		feedbackRepository.delete(id);
	}


	public Page<FeedbackDto> findPage(FeedbackReq feedbackReq) {
		PageRequest pageRequest = new PageRequest(feedbackReq.getPageNumber(), feedbackReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Feedback> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if(StringUtil.isNotNil(feedbackReq.getPhone())){
				predicates.add(builder.equal(root.get("phone"), feedbackReq.getPhone()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Feedback> feedbackPage = feedbackRepository.findAll(spec,pageRequest);
		Page<FeedbackDto> feedbackDtoPage = new PageImpl<>(FeedbackAssembler.convertToDtoList(feedbackPage.getContent()), pageRequest,feedbackPage.getTotalElements());
		return feedbackDtoPage;
	}
}
