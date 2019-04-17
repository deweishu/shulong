package com.qkl.user.assembler;


import com.qkl.user.dto.SignLogDto;
import com.qkl.user.entity.SignLog;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class SignLogAssembler {

	public static SignLog convertToEntity(SignLogDto signLogDto, SignLog signLog){
		if(signLog==null){
			signLog = new SignLog();
		}
		signLog.setId(signLogDto.getId());
		signLog.setSignDate(signLogDto.getSignDate());
		signLog.setCandyNum(signLogDto.getCandyNum());
		return signLog;
	}


	public static SignLogDto convertToDto(SignLog signLog){
		if(signLog==null){
			return null;
		}
		SignLogDto signLogDto = new SignLogDto();
		signLogDto.setId(signLog.getId());
		signLogDto.setUserId(signLog.getUser()==null?null:signLog.getUser().getId());
		signLogDto.setSignDate(signLog.getSignDate());
		signLogDto.setCandyNum(signLog.getCandyNum());
		signLogDto.setCreateTime(signLog.getCreateTime());
		signLogDto.setUpdateTime(signLog.getUpdateTime());
		return signLogDto;
	}


	public static List<SignLogDto> convertToDtoList(List<SignLog> signLogList){
		List<SignLogDto> signLogDtoList= new ArrayList<>();
		signLogList.forEach(signLog -> signLogDtoList.add(convertToDto(signLog)));
		return signLogDtoList;
	}
}
