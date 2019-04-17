package com.qkl.user.service;


import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.TimeUtil;
import com.qkl.user.bean.CandyLogType;
import com.qkl.user.cache.CandyLogCache;
import com.qkl.user.cache.SignCache;
import com.qkl.user.cache.SignDayCache;
import com.qkl.user.dto.SignLogDto;
import com.qkl.user.entity.CandyLog;
import com.qkl.user.entity.User;
import com.qkl.user.jpa.CandyLogRepository;
import com.qkl.user.jpa.SignLogRepository;
import com.qkl.user.dto.SignLogReq;
import com.qkl.user.assembler.SignLogAssembler;
import com.qkl.user.entity.SignLog;
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
*签到业务处理
generate by dengjihai
*/
@Service
public class SignLogService {

	@Autowired
	private SignLogRepository signLogRepository;

	@Autowired
	SignCache signCache;

	@Autowired
	ConfigService configService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CandyLogRepository candyLogRepository;

	@Autowired
	CandyLogCache candyLogCache;

	@Autowired
	SignDayCache signDayCache;

	/**
	 * 签到
	 * @param userId
	 * @param ip
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String save(String userId,String ip){
		User user=userRepository.findOne(userId);
		Assert.notNull(user,"不存在该用户信息");
		String date = TimeUtil.dateToString(new Date(),TimeUtil.datePattern);
		String cacheSign=signCache.getSign(userId);
		if(cacheSign==null || (cacheSign!=null && !cacheSign.equals(date))){
			ConfigDto configDto=configService.getConfigByKey(SysConfigKey.SIGN_CANDY.getCode());
			String configStr=configDto.getConfigContent();
			String []str=configStr.split(",");
			Integer dayNum=signDayCache.getDayNum(userId);
			//如果已经达到7天，那就重新开始，从第0天开始
			if(dayNum.equals(CodeConstant.DAY_NUM) ||dayNum.equals(0) ){
				dayNum=0;
			}
			Integer candyNum;
			if(dayNum.equals(0)){
				candyNum=Integer.parseInt(str[0]);
			}else{
				candyNum=Integer.parseInt(str[dayNum-1]);
			}
			SignLog signIn = new SignLog();
			signIn.setUser(user);
			signIn.setSignDate(date);
			signIn.setCandyNum(candyNum);
			signIn.setSignIp(ip);
			addCandyLog(user,candyNum);
			signCache.save(userId,date);
			String id=signLogRepository.save(signIn).getId();
			signDayCache.save(userId,dayNum+1);
			return id;
		}
		return null;
	}

	/**
	 * 添加糖果变动日志
	 * @param user
	 * @param num
	 */
	void addCandyLog(User user,Integer num){
		CandyLog candyLog =new CandyLog();
		candyLog.setUser(user);
		candyLog.setCandyNum(num);
		candyLog.setChangeType(CandyLogType.SIGN_GET);
		candyLog.setChangeDesc(CandyLogType.SIGN_GET.getName()+",送"+num+"个糖果");
		candyLogRepository.save(candyLog);
		//删除糖果记录缓存
		candyLogCache.removeCandy(user.getId());

		user.setCandyAmount(user.getCandyAmount()+num);
		userRepository.save(user);

	}




	public SignLogDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return SignLogAssembler.convertToDto(signLogRepository.findOne(id));
	}


	public Page<SignLogDto> findPage(SignLogReq signLogReq) {
		PageRequest pageRequest = new PageRequest(signLogReq.getPageNumber(), signLogReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<SignLog> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if(StringUtil.isNotNil(signLogReq.getUserId())){
				predicates.add(builder.equal(root.join("user").get("id").as(String.class),signLogReq.getUserId()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<SignLog> signLogPage = signLogRepository.findAll(spec,pageRequest);
		Page<SignLogDto> signLogDtoPage = new PageImpl<>(SignLogAssembler.convertToDtoList(signLogPage.getContent()), pageRequest,signLogPage.getTotalElements());
		return signLogDtoPage;
	}
}
