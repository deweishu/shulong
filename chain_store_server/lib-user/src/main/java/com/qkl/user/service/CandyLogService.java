package com.qkl.user.service;


import com.qkl.apk.entity.Apk;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.common.util.TimeUtil;
import com.qkl.user.bean.CandyLogType;
import com.qkl.user.cache.CandyLogCache;
import com.qkl.user.dto.CandyLogDto;
import com.qkl.user.entity.User;
import com.qkl.user.jpa.CandyLogRepository;
import com.qkl.user.dto.CandyLogReq;
import com.qkl.user.assembler.CandyLogAssembler;
import com.qkl.user.entity.CandyLog;
import com.qkl.user.jpa.UserRepository;
import com.rabbitmq.client.AMQP;
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
//糖果日志业务
@Service
public class CandyLogService {

	private Logger logger= LoggerFactory.getLogger(CandyLogService.class);

	@Autowired
	private CandyLogRepository candyLogRepository;

	@Autowired
	CandyLogCache candyLogCache;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApkRepository apkRepository;

	@Transactional(rollbackFor = Exception.class)
	public String save(CandyLogDto candyLogDto){
		CandyLog  candyLog;
		Apk apk=apkRepository.findOne(candyLogDto.getChangeId());
		String date= TimeUtil.dateFormat(new Date());
		if(apk==null){
			logger.error("## 不存在该app信息，没有糖果数");
			return null;
		}
		logger.info("\n #### app:{}, 糖果数:{}",apk.getName(),apk.getCandyNum());
		if (StringUtil.isNotNil(candyLogDto.getId())) {
			candyLog=candyLogRepository.findOne(candyLogDto.getId());
			Assert.notNull(candyLog, "不存该数据");
			candyLog = CandyLogAssembler.convertToEntity(candyLogDto, candyLog);
		}else{
			candyLog =CandyLogAssembler.convertToEntity(candyLogDto,null);
		}
		User user=userRepository.findOne(candyLogDto.getUserId());
		Assert.notNull(user,"用户不存在");
		boolean flag =true;
		//如果是下载APP所得糖果，查询是否已经获取过，如果已经存在，那就不予进行增加糖果数
		if(candyLogDto.getChangeType().equals(CandyLogType.DOWN_APP.getCode())){
			CandyLog candyLog1=candyLogRepository.findByUser_IdAndChangeId(candyLogDto.getUserId(),candyLogDto.getChangeId());
			if(candyLog1!=null){
				candyLog=candyLog1;
				flag=false;
				logger.info("\n #### 已经下载过该APP,不予添加糖果");
			}else{
				user.setCandyAmount(user.getCandyAmount()+apk.getCandyNum());
				candyLog.setCandyNum(apk.getCandyNum());
			}
		}
		else if(candyLogDto.getChangeType().equals(CandyLogType.APP_SHARE.getCode())){ // 应用分享
//			List<CandyLog> candyLogList=candyLogRepository.findByUser_IdAndChangeType(candyLogDto.getUserId(),CandyLogType.APP_SHARE);
			List<CandyLog> candyLogList=candyLogRepository.findByUser_IdAndChangeTypeAndChangeIdAndChangeDate(candyLogDto.getUserId(),CandyLogType.APP_SHARE,candyLogDto.getChangeId(),date);
			Integer maxNum=apk.getShareMaxNum();
			logger.info("\n 该应用限制每天分享次数：{}，已经分享{}次",maxNum,candyLogList.size());
			if(maxNum!=null){
				if(candyLogList.size()==maxNum){
					candyLog=candyLogList.get(0);
					flag=false;
				}else{
					candyLog.setCandyNum(apk.getShareCandy());
					user.setCandyAmount(user.getCandyAmount()+apk.getShareCandy());
				}
			}else{
				flag=false;
			}
		}
		if(apk.getCandyNum()>0 && flag){
			userRepository.save(user);
			candyLog.setUser(user);
			candyLog.setChangeDate(date);
			candyLogCache.removeCandy(user.getId());
			return candyLogRepository.save(candyLog).getId();
		}else{
			return null;
		}

	}










	/**
	 * 通过评论获取糖果
	 * @param apkId
	 * @param userId
	 */
	public void addCandyByComment(String apkId,String userId){
		Apk apk=apkRepository.findOne(apkId);
		Assert.notNull(apk,"不存在该应用信息");
		User user=userRepository.findOne(userId);
		Assert.notNull(user,"不存在该用户信息");
		Integer candyNum=apk.getCommentCandy();//评论送的糖果数
		user.setCandyAmount(user.getCandyAmount()+candyNum);
		userRepository.save(user);
		String date= TimeUtil.dateFormat(new Date());
		CandyLog  candyLog = new CandyLog();
		candyLog.setUser(user);
		candyLog.setChangeDate(date);
		candyLog.setCandyNum(candyNum);
		candyLog.setChangeType(CandyLogType.COMMENT_APP);
		candyLog.setChangeDesc(CandyLogType.COMMENT_APP.getName()+"，获取糖果");
		candyLogRepository.save(candyLog).getId();
	}


	public CandyLogDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return CandyLogAssembler.convertToDto(candyLogRepository.findOne(id));
	}


	public Page<CandyLogDto> findPage(CandyLogReq candyLogReq) {
		PageRequest pageRequest = new PageRequest(candyLogReq.getPageNumber(), candyLogReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<CandyLog> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if(StringUtil.isNotNil(candyLogReq.getUserId())){
				predicates.add(builder.equal(root.join("user").get("id").as(String.class),candyLogReq.getUserId()));
			}

			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<CandyLog> candyLogPage = candyLogRepository.findAll(spec,pageRequest);
		Page<CandyLogDto> candyLogDtoPage = new PageImpl<>(CandyLogAssembler.convertToDtoList(candyLogPage.getContent()), pageRequest,candyLogPage.getTotalElements());
		return candyLogDtoPage;
	}

	/**
	 * 通过抽奖减少糖果数量
	 * @param ChangType
	 * @param userId
	 */
	public void  cutbackCandyByComment(String ChangType,String userId){

	}



	/**
	 * 根据userid，查询出糖果明细
	 * @param userId
	 * @return
	 */
	public List<CandyLogDto> getCandyLog(String userId){

		List<CandyLogDto> candyLogDtoList=candyLogCache.getCandyLog(userId);
		if(candyLogDtoList!=null){
			return candyLogDtoList;
		}

		List<CandyLog> candyLogList=candyLogRepository.findByUser_IdOrderByCreateTimeDesc(userId);

		List<CandyLogDto> candyLogDtoList1 = CandyLogAssembler.convertToDtoList(candyLogList);
		candyLogCache.save(userId,candyLogDtoList1);
		return candyLogDtoList1;
	}


}
