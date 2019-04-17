package com.qkl.msg.assembler;


import com.qkl.common.bean.JpaProperty;
import com.qkl.msg.bean.PushType;
import com.qkl.msg.dto.PushLogDto;
import com.qkl.msg.entity.PushLog;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class PushLogAssembler {

	public static PushLog convertToEntity(PushLogDto pushLogDto, PushLog pushLog){
		if(pushLog==null){
			pushLog = new PushLog();
		}
		pushLog.setId(pushLogDto.getId());
		pushLog.setPushTitle(pushLogDto.getPushTitle());
		pushLog.setPushTxt(pushLogDto.getPushTxt());
		pushLog.setPushContent(pushLogDto.getPushContent());
		pushLog.setOperateId(pushLogDto.getOperateId());
		pushLog.setOperateName(pushLogDto.getOperateName());
		pushLog.setPushType(pushLogDto.getPushType()==null? PushType.GROUP: JpaProperty.getProperty(PushType.class,pushLogDto.getPushType()));
		pushLog.setPushResult(pushLogDto.getPushResult());
		return pushLog;
	}


	public static PushLogDto convertToDto(PushLog pushLog){
		if(pushLog==null){
			return null;
		}
		PushLogDto pushLogDto = new PushLogDto();
		pushLogDto.setId(pushLog.getId());
		pushLogDto.setCreateTime(pushLog.getCreateTime());
		pushLogDto.setUpdateTime(pushLog.getUpdateTime());
		pushLogDto.setPushTitle(pushLog.getPushTitle());
		pushLogDto.setPushTxt(pushLog.getPushTxt());
		pushLogDto.setPushContent(pushLog.getPushContent());
		pushLogDto.setOperateId(pushLog.getOperateId());
		pushLogDto.setOperateName(pushLog.getOperateName());
		pushLogDto.setPushType(pushLog.getPushType().getCode());
		pushLogDto.setPushResult(pushLog.getPushResult());
		return pushLogDto;
	}


	public static List<PushLogDto> convertToDtoList(List<PushLog> pushLogList){
		List<PushLogDto> pushLogDtoList= new ArrayList<>();
		pushLogList.forEach(pushLog -> pushLogDtoList.add(convertToDto(pushLog)));
		return pushLogDtoList;
	}
}
