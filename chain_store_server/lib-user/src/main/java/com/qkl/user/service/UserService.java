package com.qkl.user.service;


import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.cache.ConfigCache;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.RandomUtil;
import com.qkl.common.util.ServiceAssert;
import com.qkl.common.util.TimeUtil;
import com.qkl.common.web.AppException;
import com.qkl.common.web.StandardResponseHeader;
import com.qkl.user.bean.CandyLogType;
import com.qkl.user.bean.ThirdLoginType;
import com.qkl.user.cache.CandyLogCache;
import com.qkl.user.cache.UserInvetCache;
import com.qkl.user.cache.UserInvetMinstueCache;
import com.qkl.user.dto.*;
import com.qkl.user.entity.CandyLog;
import com.qkl.user.jpa.CandyLogRepository;
import com.qkl.user.jpa.UserRepository;
import com.qkl.user.assembler.UserAssembler;
import com.qkl.user.entity.User;
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
public class UserService {

	private Logger logger= LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ConfigService configService;

	@Autowired
	CandyLogRepository candyLogRepository;

	@Autowired
	CandyLogCache candyLogCache;

	@Autowired
	UserInvetCache userInvetCache;


	@Autowired
    UserInvetMinstueCache userInvetMinstueCache;
	/**
	 * 第三方账号登录
	 * @param userDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserLoginResp thirdLogin(UserDto userDto){
		User user=userRepository.findByThirdId(userDto.getThirdId());
		UserLoginResp userLoginResp = new UserLoginResp();
		if(user!=null){
			userLoginResp.setCandyAmount(user.getCandyAmount());
			userLoginResp.setHeadImg(user.getHeadImg());
			userLoginResp.setMobile(user.getMobile());
			userLoginResp.setId(user.getId());
			userLoginResp.setNickName(user.getNickName());
			userLoginResp.setInvetCode(user.getInvetCode());
			if(StringUtil.isNotNil(userDto.getGtClientId())){
				user.setGtClientId(userDto.getGtClientId());
				userRepository.save(user);
			}
		}else{
			//创建第三方账号登录
			ConfigDto configDto=configService.getConfigByKey(SysConfigKey.REG_CANDY.getCode());
			Integer candyAmount=configDto.getConfigNum();
			user =new User();
			user.setCandyAmount(candyAmount);
			user.setHeadImg(userDto.getHeadImg());
			user.setThirdId(userDto.getThirdId());
			user.setThridType(JpaProperty.getProperty(ThirdLoginType.class,userDto.getThirdType()));
			user.setNickName(userDto.getNickName());
			user.setStatus(true);
			user.setInvetCode(generateCustInviteCode(CodeConstant.INVET_MAX_NUM));
			if(StringUtil.isNotNil(userDto.getGtClientId())){
				user.setGtClientId(userDto.getGtClientId());
			}
			String id=userRepository.save(user).getId();
			Assert.notNull(id,"登录失败");

			userLoginResp.setCandyAmount(user.getCandyAmount());
			userLoginResp.setHeadImg(user.getHeadImg());
			userLoginResp.setId(id);
			userLoginResp.setNickName(userDto.getNickName());
			userLoginResp.setInvetCode(user.getInvetCode());
		}
		Assert.isTrue(user.getStatus(),"用户状态异常");
		//更新客户端的 个推推送id
		return userLoginResp;
	}


	/**
	 * 手机号登录
	 * @param loginReq
	 * @return
	 */
	public UserLoginResp userLogin(LoginReq loginReq){
		UserLoginResp userLoginResp = new UserLoginResp();
		User user=userRepository.findByMobile(loginReq.getMobile());
		Assert.notNull(user,"不存在该用户信息");
		Assert.isTrue(user.getPassWord().equals(loginReq.getPassWord()),"密码错误");
		Assert.isTrue(user.getStatus(),"用户状态异常");
		userLoginResp.setCandyAmount(user.getCandyAmount());
		userLoginResp.setHeadImg(user.getHeadImg());
		userLoginResp.setMobile(user.getMobile());
		userLoginResp.setId(user.getId());
		userLoginResp.setNickName(user.getNickName());
		userLoginResp.setInvetCode(user.getInvetCode());
		//更新客户端的 个推推送id
		if(StringUtil.isNotNil(loginReq.getGtClientId())){
			user.setGtClientId(loginReq.getGtClientId());
			userRepository.save(user);
		}
		return userLoginResp;
	}


	/**
	 * 手机号注册
	 * @param loginReq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserLoginResp save(LoginReq loginReq){
		User user =new User();
		if(StringUtil.isNotNil(loginReq.getInvetCode())){
			User invetUser=userRepository.findByInvetCode(loginReq.getInvetCode());
			Assert.notNull(invetUser,"邀请码无效");//不存在该邀请码
			Assert.isTrue(invetUser.getStatus(),"邀请码无效");//邀请者已经被封号。
			user.setInviteUser(invetUser);
		}
		User userRepositoryByMobile=userRepository.findByMobile(loginReq.getMobile());
		Assert.isTrue(userRepositoryByMobile==null,"该手机号已经注册");
		ConfigDto configDto=configService.getConfigByKey(SysConfigKey.REG_CANDY.getCode());
		Integer candyAmount=configDto.getConfigNum();
		user.setCandyAmount(candyAmount);
		user.setMobile(loginReq.getMobile());
		user.setPassWord(loginReq.getPassWord());
		user.setStatus(true);
		user.setInvetCode(generateCustInviteCode(CodeConstant.INVET_MAX_NUM));
		//更新客户端的 个推推送id
		if(StringUtil.isNotNil(loginReq.getGtClientId())){
			user.setGtClientId(loginReq.getGtClientId());
		}
		String userId=userRepository.save(user).getId();
		UserLoginResp loginResp = new UserLoginResp();
		loginResp.setMobile(loginReq.getMobile());
		loginResp.setId(userId);
		loginResp.setCandyAmount(candyAmount);
		loginResp.setInvetCode(user.getInvetCode());
		loginResp.setHeadImg(user.getHeadImg());
		loginResp.setNickName(user.getNickName());
		addCandyLog(user,candyAmount);
		return loginResp;
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
		candyLog.setChangeType(CandyLogType.REG_GET);
		candyLog.setChangeDesc(CandyLogType.REG_GET.getName()+",送"+num+"个糖果");
		candyLogRepository.save(candyLog);
		//删除糖果记录缓存
		candyLogCache.removeCandy(user.getId());
	}

	public UserDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return UserAssembler.convertToDto(userRepository.findOne(id));
	}


	public List<UserDto> findAll() {
		return UserAssembler.convertToDtoList(userRepository.findByOrderByCreateTimeDesc());
	}

	public UserDto findByInvetCode(String invetCode) {
		Assert.isTrue(StringUtil.isNotBlank(invetCode), "邀请码不能为空");
		return UserAssembler.convertToDto(userRepository.findByInvetCode(invetCode));
	}

	public UserDto findByMobile(String mobile) {
		Assert.isTrue(StringUtil.isNotBlank(mobile), "手机号不能为空");
		return UserAssembler.convertToDto(userRepository.findByMobile(mobile));
	}



	/**
	 * 修改用户状态
	 * @param userId
	 * @param status
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String updateStatus(String userId,Boolean status){
		User user=userRepository.findOne(userId);
		Assert.notNull(user,"不存在该用户");
		user.setStatus(status);
		String id=userRepository.save(user).getId();
		return id;
	}


	/**
	 * 修改用户头像
	 * @param userId
	 * @param headImg
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String updateHeadImg(String userId,String headImg){
		User user=userRepository.findOne(userId);
		Assert.notNull(user,"不存在该用户");
		user.setHeadImg(headImg);
		String id=userRepository.save(user).getId();
		return id;
	}

	/**
	 * 修改用户昵称
	 * @param userId
	 * @param nickName
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String updateNick(String userId,String nickName){
		User user=userRepository.findOne(userId);
		Assert.notNull(user,"不存在该用户");
		user.setNickName(nickName);
		String id=userRepository.save(user).getId();
		return id;
	}


	/**
	 * 修改密码
	 * @param userId
	 * @param pwdReq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String updatePwd(String userId,UpdatePwdReq pwdReq){
		User user=userRepository.findOne(userId);
		Assert.notNull(user,"不存在该用户");
		Assert.isTrue(pwdReq.getOldPass().equals(user.getPassWord()),"旧密码不正确");
		Assert.isTrue(pwdReq.getNewConfirmPass().equals(pwdReq.getNewPass()),"新密码确认错误");
		user.setPassWord(pwdReq.getNewConfirmPass());
		String id=userRepository.save(user).getId();
		return id;
	}


	/**
	 * 重置密码
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String restPwd(UpdatePwdReq pwdReq){
		User user=userRepository.findByMobile(pwdReq.getPhone());
		Assert.notNull(user,"不存在该用户");
		Assert.isTrue(pwdReq.getNewConfirmPass().equals(pwdReq.getNewPass()),"新密码确认错误");
		user.setPassWord(pwdReq.getNewConfirmPass());
		String id=userRepository.save(user).getId();
		return id;
	}

	public Page<UserDto> findPage(UserReq userReq) {
		PageRequest pageRequest = new PageRequest(userReq.getPageNumber(), userReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<User> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if (StringUtil.isNotBlank(userReq.getMobile())) {
				predicates.add(builder.equal(root.get("mobile"), userReq.getMobile()));
			}
			if(StringUtil.isNotNil(userReq.getInvetId())){
				predicates.add(builder.equal(root.join("inviteUser").get("id").as(String.class),userReq.getInvetId()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<User> userPage = userRepository.findAll(spec,pageRequest);
		Page<UserDto> userDtoPage = new PageImpl<>(UserAssembler.convertToDtoList(userPage.getContent()), pageRequest,userPage.getTotalElements());
		return userDtoPage;
	}

	/**
	 * 用户邀请码
	 *
	 * @param maxTimes 最大执行次数
	 * @return
	 */
	private String generateCustInviteCode(int maxTimes) {
		--maxTimes;
		ServiceAssert.isTrue(maxTimes > 0, StandardResponseHeader.ERROR_GENERATE_INVITE_CODE);

		String randomCode = RandomUtil.getHardRandomCode(CodeConstant.INVET_CODE_LENGTH);
		randomCode = randomCode.toUpperCase();  // 转大写
		// 库中校验
		User target = userRepository.findByInvetCode(randomCode);
		if (null != target) {
			logger.info("\n###### 邀请码 [{}]已存在，邀请码生成方法继续执行！maxTimes[{}]", randomCode, maxTimes);
			return generateCustInviteCode(maxTimes);
		} else {
			return randomCode;
		}
	}


	/**
	 * 邀请注册送糖果
	 * @param invetCode
	 */
	public void addInvetCandy(String invetCode){
		//一级邀请人
		User user1=userRepository.findByInvetCode(invetCode);
		Assert.notNull(user1,"该邀请码不存在");

        Integer minstueCache=userInvetMinstueCache.getNum(user1.getId());
        //一分钟之内邀请了两个人的
        if(minstueCache.intValue()>=2){
            Assert.notNull(user1,"邀请频率太高");
            logger.error("\n ### 邀请频率太高，不予增加糖果，并且封号,{}",user1.getInvetCode());
            user1.setStatus(false);
            userRepository.save(user1);
            return;
        }

        List<User> userList=userRepository.findByInviteUserOrderByCreateTimeDesc(user1);
        if(userList!=null && userList.size()>0){
            User lastUser=userList.get(0);//取出最近一个被邀请人的时间
            Date lastTime=lastUser.getCreateTime();
            long interval = (TimeUtil.now().getTime() - lastTime.getTime())/1000;
            //如果60秒之内邀请了一个人，就往缓存增加+1
            if(interval<60){
                Integer minstueCacheNum=userInvetMinstueCache.getNum(user1.getId());
                userInvetMinstueCache.saveNum(user1.getId(),minstueCacheNum+1);
            }
        }


		Integer peopleNum=userInvetCache.getNum(user1.getId());
		logger.info("\n 用户：{},邀请人数：{}",user1.getMobile(),peopleNum);
		//如果半个小时以内，邀请人数大于30人，那就直接封号。
		if(peopleNum.intValue()>=30){
			user1.setStatus(false);
			userRepository.save(user1);
			return;
		}


		//一级邀请人增加糖果数
		ConfigDto configDto=configService.getConfigByKey(SysConfigKey.INVITE_FRIEND.getCode());
		Integer candy=user1.getCandyAmount();
		user1.setCandyAmount(candy+configDto.getConfigNum());
		userRepository.save(user1);
		addCandyLogByInvet(user1,configDto.getConfigNum());
		userInvetCache.saveNum(user1.getId(),peopleNum+1);
		//二级邀请人
		User user2=user1.getInviteUser();
		if(user2!=null){
			//二级邀请人增加糖果数
			ConfigDto configDto1=configService.getConfigByKey(SysConfigKey.INVITE_FRIEND_SECOND.getCode());
			Integer candyAmount=user2.getCandyAmount();
			user2.setCandyAmount(candyAmount+configDto1.getConfigNum());
			userRepository.save(user2);
			addCandyLogByInvet(user2,configDto1.getConfigNum());
			//三级邀请人
			User user3=user2.getInviteUser();
			if(user3!=null){
				//三级邀请人增加糖果数
				ConfigDto configDto2=configService.getConfigByKey(SysConfigKey.INVITE_FRIEND_THIRD.getCode());
				Integer candyAmount1=user3.getCandyAmount();
				user3.setCandyAmount(candyAmount1+configDto2.getConfigNum());
				userRepository.save(user3);
				addCandyLogByInvet(user3,configDto2.getConfigNum());
			}
		}

	}

	/**
	 * 添加糖果变动日志
	 * @param user
	 * @param num
	 */
	void addCandyLogByInvet(User user,Integer num){
		CandyLog candyLog =new CandyLog();
		candyLog.setUser(user);
		candyLog.setCandyNum(num);
		candyLog.setChangeType(CandyLogType.SHARE_FRIEND);
		candyLog.setChangeDesc(CandyLogType.SHARE_FRIEND.getName()+",送"+num+"个糖果");
		candyLogRepository.save(candyLog);
		//删除糖果记录缓存
		candyLogCache.removeCandy(user.getId());
	}

}
