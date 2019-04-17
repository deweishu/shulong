package com.qkl.user.assembler;


import com.qkl.common.bean.JpaProperty;
import com.qkl.user.bean.CandyLogType;
import com.qkl.user.dto.CandyLogDto;
import com.qkl.user.entity.CandyLog;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class CandyLogAssembler {

	public static CandyLog convertToEntity(CandyLogDto candyLogDto, CandyLog candyLog){
		if(candyLog==null){
			candyLog = new CandyLog();
		}
		candyLog.setCandyNum(candyLogDto.getCandyNum());
		CandyLogType candyLogType = JpaProperty.getProperty(CandyLogType.class,candyLogDto.getChangeType());
		candyLog.setChangeDesc(candyLogType.getName()+"获得糖果");
		candyLog.setChangeType(candyLogDto.getChangeType()==null?null:JpaProperty.getProperty(CandyLogType.class,candyLogDto.getChangeType()));
		candyLog.setChangeId(candyLogDto.getChangeId());
		return candyLog;
	}


	public static CandyLogDto convertToDto(CandyLog candyLog){
		if(candyLog==null){
			return null;
		}
		CandyLogDto candyLogDto = new CandyLogDto();
		candyLogDto.setId(candyLog.getId());
		candyLogDto.setUserId(candyLog.getUser()!=null?candyLog.getUser().getId():null);
		candyLogDto.setCandyNum(candyLog.getCandyNum());
		candyLogDto.setChangeDesc(candyLog.getChangeDesc());
		candyLogDto.setChangeType(candyLog.getChangeType()==null?null: candyLog.getChangeType().getCode());
		candyLogDto.setChangeId(candyLog.getChangeId());
		candyLogDto.setCreateTime(candyLog.getCreateTime());
		candyLogDto.setUpdateTime(candyLog.getUpdateTime());
		return candyLogDto;
	}


	public static List<CandyLogDto> convertToDtoList(List<CandyLog> candyLogList){
		List<CandyLogDto> candyLogDtoList= new ArrayList<>();
		candyLogList.forEach(candyLog -> candyLogDtoList.add(convertToDto(candyLog)));
		return candyLogDtoList;
	}
}
