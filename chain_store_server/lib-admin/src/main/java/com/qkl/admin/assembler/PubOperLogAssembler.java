package com.qkl.admin.assembler;



import com.qkl.admin.dto.PubOperLogDto;
import com.qkl.admin.entity.PubOperLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benson on 2018/3/13.
 */
public class PubOperLogAssembler {

    public static PubOperLog convertToEntity(PubOperLogDto operLogDto, PubOperLog entity){
        if(entity==null){
            entity = new PubOperLog();
        }
        entity.setId(operLogDto.getId());
        entity.setOperatorId(operLogDto.getOperatorId());
        entity.setMethod(operLogDto.getMethod());
        entity.setParameter(operLogDto.getParameter());
        entity.setUri(operLogDto.getUri());
        entity.setModule(operLogDto.getModule());
        entity.setOperatePhone(operLogDto.getOperatePhone());
        entity.setOperateContent(operLogDto.getOperateContent());
        entity.setOperateName(operLogDto.getOperateName());
        return entity;
    }


    public static PubOperLogDto convertToDto(PubOperLog entity){
        if (null==entity) return null;

        PubOperLogDto dictDto = new PubOperLogDto(entity.getId(),entity.getModule(),entity.getOperatorId(),entity.getMethod(),
                entity.getUri(),entity.getParameter(), entity.getOperateName(), entity.getOperatePhone(), entity.getOperateContent(),entity.getCreateTime());

        return dictDto;
    }


    public static List<PubOperLogDto> convertToDtoList(List<PubOperLog> operLogs){
        List<PubOperLogDto> dtos= new ArrayList<>();
        operLogs.forEach(entity -> dtos.add(convertToDto(entity)));
        return dtos;
    }
    
}
