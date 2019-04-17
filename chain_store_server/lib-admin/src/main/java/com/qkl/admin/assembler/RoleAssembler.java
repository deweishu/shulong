package com.qkl.admin.assembler;

import com.qkl.admin.dto.RoleDto;
import com.qkl.admin.entity.Role;
import com.qkl.common.bean.BaseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengjihai on 2018/3/9.
 */
public class RoleAssembler {

    public static Role convertToEntity(RoleDto roleDto, Role role){
        if(role==null){
            role = new Role();
        }
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setStatus(null==roleDto.getStatus()? BaseStatus.DISABLED:BaseStatus.getProperty(BaseStatus.class, roleDto.getStatus()));
        return role;
    }


    public static RoleDto convertToDto(Role admin){
        if (null==admin) return null;
        RoleDto adminDto = new RoleDto(admin.getId(),admin.getName(),admin.getStatus().getCode(),admin.getCreateTime());
        return adminDto;
    }


    public static List<RoleDto> convertToDtoList(List<Role> adminList){
        List<RoleDto> adminDtoList= new ArrayList<>();
        adminList.forEach(admin -> adminDtoList.add(convertToDto(admin)) );
        return adminDtoList;
    }

}
