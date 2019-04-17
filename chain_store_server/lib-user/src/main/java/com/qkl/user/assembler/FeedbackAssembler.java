package com.qkl.user.assembler;


import com.qkl.common.util.StringUtil;
import com.qkl.user.dto.FeedbackDto;
import com.qkl.user.entity.Feedback;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class FeedbackAssembler {

	public static Feedback convertToEntity(FeedbackDto feedbackDto, Feedback feedback){
		if(feedback==null){
			feedback = new Feedback();
		}
		feedback.setId(feedbackDto.getId());
		feedback.setDescribe(feedbackDto.getDescribe());
		feedback.setPhone(feedbackDto.getPhone());
		return feedback;
	}


	public static FeedbackDto convertToDto(Feedback feedback){
		if(feedback==null){
			return null;
		}
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setId(feedback.getId());
		feedbackDto.setDescribe(feedback.getDescribe());
		feedbackDto.setPhone(feedback.getPhone());
		feedbackDto.setUserId(feedback.getUser()==null?null:feedback.getUser().getId());
		feedbackDto.setUserName(feedback.getUser()==null?null:feedback.getUser().getNickName()==null? feedback.getUser().getMobile():feedback.getUser().getNickName());
		feedbackDto.setCreateTime(feedback.getCreateTime());
		feedbackDto.setUpdateTime(feedback.getUpdateTime());
		return feedbackDto;
	}


	public static List<FeedbackDto> convertToDtoList(List<Feedback> feedbackList){
		List<FeedbackDto> feedbackDtoList= new ArrayList<>();
		feedbackList.forEach(feedback -> feedbackDtoList.add(convertToDto(feedback)));
		return feedbackDtoList;
	}
}
