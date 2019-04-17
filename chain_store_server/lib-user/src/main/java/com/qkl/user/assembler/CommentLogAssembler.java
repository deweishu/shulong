package com.qkl.user.assembler;


import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.user.dto.CommentLogDto;
import com.qkl.user.entity.CommentLog;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class CommentLogAssembler {

	public static CommentLog convertToEntity(CommentLogDto commentLogDto, CommentLog commentLog){
		if(commentLog==null){
			commentLog = new CommentLog();
		}
		commentLog.setCommentNum(commentLogDto.getCommentNum());
		commentLog.setCommentDesc(commentLogDto.getCommentDesc());
		commentLog.setMobileType(commentLogDto.getMobileType());
		commentLog.setLoveNum(commentLogDto.getLoveNum());
		commentLog.setNoNum(commentLogDto.getNoNum());
		return commentLog;
	}


	public static CommentLogDto convertToDto(CommentLog commentLog){
		if(commentLog==null){
			return null;
		}
		CommentLogDto commentLogDto = new CommentLogDto();
		commentLogDto.setId(commentLog.getId());
		commentLogDto.setCommentNum(commentLog.getCommentNum());
		commentLogDto.setCommentDesc(commentLog.getCommentDesc());
		commentLogDto.setApkId(commentLog.getApk().getId());
		commentLogDto.setUserId(commentLog.getUser()==null?null:commentLog.getUser().getId());
		commentLogDto.setUserName(commentLog.getUser()==null?null:commentLog.getUser().getNickName()==null? StringUtil.mobilePhoneReplace(commentLog.getUser().getMobile()):commentLog.getUser().getNickName());
		commentLogDto.setMobileType(commentLog.getMobileType());
		commentLogDto.setCreateTime(commentLog.getCreateTime());
		commentLogDto.setCommentDate(TimeUtil.dateFormat(commentLog.getCreateTime()));
		commentLogDto.setUpdateTime(commentLog.getUpdateTime());
		commentLogDto.setLoveNum(commentLog.getLoveNum());
		commentLogDto.setNoNum(commentLog.getNoNum());
		return commentLogDto;
	}


	public static List<CommentLogDto> convertToDtoList(List<CommentLog> commentLogList){
		List<CommentLogDto> commentLogDtoList= new ArrayList<>();
		commentLogList.forEach(commentLog -> commentLogDtoList.add(convertToDto(commentLog)));
		return commentLogDtoList;
	}
}
