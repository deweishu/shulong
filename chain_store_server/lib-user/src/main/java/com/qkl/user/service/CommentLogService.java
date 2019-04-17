package com.qkl.user.service;


import com.qkl.apk.entity.Apk;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.user.cache.CommentLoveNumCache;
import com.qkl.user.cache.CommentNoNumCache;
import com.qkl.user.cache.UserCommentCache;
import com.qkl.user.dto.CommentLogDto;
import com.qkl.user.entity.User;
import com.qkl.user.jpa.CommentLogRepository;
import com.qkl.user.dto.CommentLogReq;
import com.qkl.user.assembler.CommentLogAssembler;
import com.qkl.user.entity.CommentLog;
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
//评论内容业务
@Service
public class CommentLogService {

	@Autowired
	private CommentLogRepository commentLogRepository;


	@Autowired
	ApkRepository apkRepository;

	@Autowired
	UserRepository userRepository;


	@Autowired
	UserCommentCache commentCache;

	@Autowired
	CommentLoveNumCache commentLoveNumCache;

	@Autowired
	CommentNoNumCache commentNoNumCache;

	@Autowired
	CandyLogService candyLogService;

	/**
	 * 添加评论内容
	 * @param commentLogDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String save(CommentLogDto commentLogDto){
		CommentLog  commentLog;
		if (StringUtil.isNotNil(commentLogDto.getId())) {
			commentLog=commentLogRepository.findOne(commentLogDto.getId());
			Assert.notNull(commentLog, "不存该数据");
			commentLog = CommentLogAssembler.convertToEntity(commentLogDto, commentLog);
		}else{
			commentLog =CommentLogAssembler.convertToEntity(commentLogDto,null);
		}
		User user=userRepository.findOne(commentLogDto.getUserId());
		Assert.notNull(user,"不存在该用户");
		Apk apk=apkRepository.findOne(commentLogDto.getApkId());
		Assert.notNull(apk,"不存在该应用");
		CommentLog commentLogRepositoryByUserIdAndApkId=commentLogRepository.findByUser_IdAndApk_Id(user.getId(),apk.getId());
		Assert.isTrue(commentLogRepositoryByUserIdAndApkId==null,"您已经评论过，不能再进行评论");
		commentLog.setApk(apk);
		commentLog.setUser(user);
		String comentId=commentLogRepository.save(commentLog).getId();
		//评论获取糖果接口
		candyLogService.addCandyByComment(commentLogDto.getApkId(),commentLogDto.getUserId());
		return comentId;
	}




	public CommentLogDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return CommentLogAssembler.convertToDto(commentLogRepository.findOne(id));
	}

	public void delete(String id){
		commentLogRepository.delete(id);
	}


	public Page<CommentLogDto> findPage(CommentLogReq commentLogReq) {
		PageRequest pageRequest = new PageRequest(commentLogReq.getPageNumber(), commentLogReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<CommentLog> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();

			if(StringUtil.isNotNil(commentLogReq.getApkId())){
				predicates.add(builder.equal(root.join("apk").get("id").as(String.class),commentLogReq.getApkId()));
			}

			if(StringUtil.isNotNil(commentLogReq.getUserId())){
				predicates.add(builder.equal(root.join("user").get("id").as(String.class),commentLogReq.getUserId()));
			}

			if(StringUtil.isNotNil(commentLogReq.getCommentDesc())){
				predicates.add(builder.like(root.get("commentDesc"), "%" +commentLogReq.getCommentDesc()+ "%", '/'));
			}

			if(commentLogReq.getCommentNum()!=null){
				predicates.add(builder.equal(root.get("commentNum"), commentLogReq.getCommentNum()));
			}

			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<CommentLog> commentLogPage = commentLogRepository.findAll(spec,pageRequest);
		Page<CommentLogDto> commentLogDtoPage = new PageImpl<>(CommentLogAssembler.convertToDtoList(commentLogPage.getContent()), pageRequest,commentLogPage.getTotalElements());
		return commentLogDtoPage;
	}

	/**
	 * 统计出apk总共的分数
	 * @param apkId
	 * @return
	 */
	public Integer sumApkNum(String apkId){
		return commentLogRepository.sumApkNum(apkId);
	}


	/**
	 * 中评（2-3分）
	 * @param apkId
	 * @return
	 */
	public Integer getCommentScore(String apkId){
		return commentLogRepository.countApkNum(apkId);
	}

	/**
	 * 好评（5），差评1分
	 * @param apkId
	 * @param commentNum
	 * @return
	 */
	public Integer getCommentScore(String apkId,Integer commentNum){
		return commentLogRepository.countByApk_IdAndCommentNumEquals(apkId,commentNum);
	}

	/**
	 * 查询出当前用户是否可以评分该app
	 * @param userId
	 * @param apkId
	 * @return
	 */
	public boolean canCommentApk(String userId,String apkId){
		Boolean canComment=commentCache.get(userId,apkId);
		if(canComment!=null){
			return canComment;
		}
		//查询数据库记录
		CommentLog commentLog=commentLogRepository.findByUser_IdAndApk_Id(userId,apkId);
		//为空。没有评论，返回可以进行评论
		if(commentLog==null){
			canComment=true;
		}else{
			//否则就是不可以进行评论
			canComment=false;
		}
		commentCache.save(userId,apkId,canComment);
		return canComment;
	}


	/**
	 * 对评论进行点赞
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public Boolean addCommentLoveNum(String commentId,String userId){
		Boolean add=commentLoveNumCache.validateAdd(commentId,userId);
		if(!add){
			return false;
		}
		CommentLog commentLog=commentLogRepository.findOne(commentId);
		Assert.notNull(commentId,"不存在该评论信息");
		commentLog.setLoveNum(commentLog.getLoveNum()+1);
		commentLogRepository.save(commentLog);
		commentLoveNumCache.save(commentId,userId);
		return true;
	}


	/**
	 * 对评论进行踩
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public Boolean subCommentNoNum(String commentId,String userId){
		Boolean add=commentLoveNumCache.validateAdd(commentId,userId);
		if(!add){
			return false;
		}
		CommentLog commentLog=commentLogRepository.findOne(commentId);
		Assert.notNull(commentId,"不存在该评论信息");
		commentLog.setNoNum(commentLog.getNoNum()+1);
		commentLogRepository.save(commentLog);
		commentNoNumCache.save(commentId,userId);
		return true;
	}



}
