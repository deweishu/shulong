package com.qkl.msg.jpa;


import com.qkl.msg.assembler.UserMsgAssembler;
import com.qkl.msg.cache.UserMsgCache;
import com.qkl.msg.dto.UserMsgDto;
import com.qkl.msg.dto.UserMsgReq;
import com.qkl.msg.jpa.UserMsgRepository;
import com.qkl.msg.entity.UserMsg;
import com.qkl.user.jpa.UserRepository;
import org.hibernate.annotations.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
public class UserMsgService {

	@Autowired
	private UserMsgRepository userMsgRepository;

	Logger logger= LoggerFactory.getLogger(UserMsgService.class);

	@Autowired
	UserMsgCache userMsgCache;

	@Autowired
	UserRepository userRepository;

	@Transactional(rollbackFor = Exception.class)
	public String save(UserMsgDto userMsgDto){
		UserMsg  userMsg;
		if (StringUtil.isNotNil(userMsgDto.getId())) {
			userMsg=userMsgRepository.findOne(userMsgDto.getId());
			Assert.notNull(userMsg, "不存该数据");
			userMsg = UserMsgAssembler.convertToEntity(userMsgDto, userMsg);
		}else{
			userMsg =UserMsgAssembler.convertToEntity(userMsgDto,null);
		}
		return userMsgRepository.save(userMsg).getId();
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(List<UserMsgDto> msgDtoList){
		List<UserMsg>	userMsgList = new ArrayList<>();
		msgDtoList.forEach(userMsgDto -> {
			UserMsg userMsg =UserMsgAssembler.convertToEntity(userMsgDto,null);
			userMsg.setUser(userRepository.findOne(userMsgDto.getUserId()));
			userMsgList.add(userMsg);
		});
		List<UserMsg> userMsgs=userMsgRepository.save(userMsgList);
		logger.info("\n 总共添加{}条消息：",userMsgs.size());
	}


	public UserMsgDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return UserMsgAssembler.convertToDto(userMsgRepository.findOne(id));
	}


	public Page<UserMsgDto> findPage(UserMsgReq userMsgReq) {
		PageRequest pageRequest = new PageRequest(userMsgReq.getPageNumber(), userMsgReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<UserMsg> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();

			if (StringUtil.isNotNil(userMsgReq.getUserId())) {
				predicates.add(builder.equal(root.join("user").get("id").as(String.class),userMsgReq.getUserId()));
			}
			if (StringUtil.isNotNil(userMsgReq.getMsgTitle())) {
				Path<String> username = root.get("msgTitle");
				predicates.add(builder.like(username, "%" + userMsgReq.getMsgTitle() + "%", '/'));
			}
			if (StringUtil.isNotNil(userMsgReq.getMsgContent())) {
				Path<String> username = root.get("msgContent");
				predicates.add(builder.like(username, "%" + userMsgReq.getMsgContent() + "%", '/'));
			}
			if (userMsgReq.getReadStatus()!=null) {
				Path<String> username = root.get("readStatus");
				predicates.add(builder.equal(username,  userMsgReq.getReadStatus()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<UserMsg> userMsgPage = userMsgRepository.findAll(spec,pageRequest);
		Page<UserMsgDto> userMsgDtoPage = new PageImpl<>(UserMsgAssembler.convertToDtoList(userMsgPage.getContent()), pageRequest,userMsgPage.getTotalElements());
		return userMsgDtoPage;
	}

	/**
	 * 通过userid查询消息
	 * @param userId
	 * @return
	 */
	public List<UserMsgDto> findByUserId(String userId){
		List<UserMsgDto> msgDtoList=userMsgCache.getByUserId(userId);
		if(msgDtoList!=null  && msgDtoList.size()>0){
				return msgDtoList;
		}
		List<UserMsg> userMsgList=userMsgRepository.findByUser_IdOrderByCreateTimeDesc(userId);
		List<UserMsgDto> userMsgDtoList=UserMsgAssembler.convertToDtoList(userMsgList);
		userMsgCache.save(userId,userMsgDtoList);
		return userMsgDtoList;
	}

	/**
	 * 删除消息
	 * @param userId
	 * @param msgId
	 */
	public void delete(String userId,String msgId){
		userMsgRepository.delete(msgId);
		userMsgCache.remove(userId);
	}

	/**
	 * 更新消息的状态
	 * @param userId
	 * @param msgId
	 * @return
	 */
	public void updateStatus(String userId,String msgId){
		if(msgId.equals("0")){
			List<UserMsg> userMsgList=userMsgRepository.findByUser_IdOrderByCreateTimeDesc(userId);
			userMsgList.forEach(userMsg -> userMsg.setReadStatus(false));
			userMsgRepository.save(userMsgList);
		}else{
			UserMsg userMsg=userMsgRepository.findOne(msgId);
			userMsg.setReadStatus(false);
			String id=userMsgRepository.save(userMsg).getId();
		}
		userMsgCache.remove(userId);
	}
}
