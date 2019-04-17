package com.qkl.msg.assembler;


import com.qkl.msg.dto.UserMsgDto;
import com.qkl.msg.entity.UserMsg;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class UserMsgAssembler {

	public static UserMsg convertToEntity(UserMsgDto userMsgDto, UserMsg userMsg){
		if(userMsg==null){
			userMsg = new UserMsg();
		}
		userMsg.setId(userMsgDto.getId());
		userMsg.setMsgTitle(userMsgDto.getMsgTitle());
		userMsg.setMsgContent(userMsgDto.getMsgContent());
		userMsg.setReadStatus(userMsgDto.getReadStatus());
		return userMsg;
	}


	public static UserMsgDto convertToDto(UserMsg userMsg){
		if(userMsg==null){
			return null;
		}
		UserMsgDto userMsgDto = new UserMsgDto();
		userMsgDto.setId(userMsg.getId());
		userMsgDto.setUserId(userMsg.getUser()==null?null:userMsg.getUser().getId());
		userMsgDto.setMsgTitle(userMsg.getMsgTitle());
		userMsgDto.setMsgContent(userMsg.getMsgContent());
		userMsgDto.setReadStatus(userMsg.getReadStatus());
		userMsgDto.setCreateTime(userMsg.getCreateTime());
		userMsgDto.setUpdateTime(userMsg.getUpdateTime());
		userMsgDto.setCreateDate(userMsg.getCreateTime());
		return userMsgDto;
	}


	public static List<UserMsgDto> convertToDtoList(List<UserMsg> userMsgList){
		List<UserMsgDto> userMsgDtoList= new ArrayList<>();
		userMsgList.forEach(userMsg -> userMsgDtoList.add(convertToDto(userMsg)));
		return userMsgDtoList;
	}
}
