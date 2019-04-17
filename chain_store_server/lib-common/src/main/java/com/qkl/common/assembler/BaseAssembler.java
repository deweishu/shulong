package com.qkl.common.assembler;


import com.qkl.common.bean.UUIDEntity;
import com.qkl.common.dto.SimpleDto;
import com.qkl.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础装配机
 */
public class BaseAssembler {

    public static SimpleDto create(String id, String name){
        return new SimpleDto(id,name);
    }

    public static SimpleDto create(UUIDEntity entity){
        return null == entity ? null : new SimpleDto(entity.getId(),"");
    }

    public static <T extends UUIDEntity> List<SimpleDto> create(List<T> entities){
        List<SimpleDto> dtos = new ArrayList<>();
        if(CollectionUtil.isNotNil(entities)){
            entities.forEach(e -> dtos.add(create(e)));
        }
        return dtos;
    }
}
