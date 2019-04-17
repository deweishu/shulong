package com.qkl.admin.assembler;

import com.qkl.admin.dto.AdminDto;
import com.qkl.admin.entity.Admin;
import com.qkl.common.bean.BaseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengjihai on 2018/3/9.
 */
public class AdminAssembler {



    public static Admin convertToEntity(AdminDto adminDto, Admin admin){
        if(admin==null){
            admin = new Admin();
        }
        admin.setId(adminDto.getId());
        admin.setUsername(adminDto.getUsername());
        admin.setMobile(adminDto.getMobile());
        admin.setEmail(adminDto.getEmail());
        admin.setRealname(adminDto.getRealname());
        admin.setStatus(null==adminDto.getStatus()? BaseStatus.DISABLED:BaseStatus.getProperty(BaseStatus.class, adminDto.getStatus()));
        return admin;
    }


    public static AdminDto convertToDto(Admin admin){
        if (null==admin) return null;
        AdminDto adminDto = new AdminDto(admin.getId(),admin.getUsername(),admin.getEmail(),admin.getPassword(),admin.getMobile(),admin.getRealname(),admin.getStatus().getCode(),admin.getCreateTime());
        // TODO 角色
        return adminDto;
    }


    public static List<AdminDto> convertToDtoList(List<Admin> adminList){
        List<AdminDto> adminDtoList= new ArrayList<>();
        adminList.forEach(admin -> adminDtoList.add(convertToDto(admin)) );
        return adminDtoList;
    }

}
