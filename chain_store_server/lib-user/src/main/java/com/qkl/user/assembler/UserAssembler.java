package com.qkl.user.assembler;


import com.qkl.user.dto.UserDto;
import com.qkl.user.entity.User;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class UserAssembler {

	public static User convertToEntity(UserDto userDto, User user){
		if(user==null){
			user = new User();
		}
		user.setId(userDto.getId());
		user.setMobile(userDto.getMobile());
		user.setPassWord(userDto.getPassWord());
		user.setStatus(userDto.getStatus());
		user.setHeadImg(userDto.getHeadImg());
		user.setCandyAmount(userDto.getCandyAmount());
		user.setNickName(userDto.getNickName());
		user.setInvetCode(userDto.getInvetCode());
		return user;
	}


	public static UserDto convertToDto(User user){
		if(user==null){
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setMobile(user.getMobile());
		userDto.setPassWord(user.getPassWord());
		userDto.setStatus(user.getStatus());
		userDto.setHeadImg(user.getHeadImg());
		userDto.setCandyAmount(user.getCandyAmount());
		userDto.setNickName(user.getNickName());
		userDto.setInvertNickName(user.getInviteUser()==null?null:user.getInviteUser().getNickName()==null? user.getInviteUser().getMobile():user.getInviteUser().getNickName());
		userDto.setInvetCode(user.getInvetCode());
		userDto.setThirdType(user.getThridType()==null?0:user.getThridType().getCode());
		userDto.setThirdId(user.getThirdId()==null?"":user.getThirdId());
		userDto.setCreateTime(user.getCreateTime());
		return userDto;
	}


	public static List<UserDto> convertToDtoList(List<User> userList){
		List<UserDto> userDtoList= new ArrayList<>();
		userList.forEach(user -> userDtoList.add(convertToDto(user)));
		return userDtoList;
	}
}
