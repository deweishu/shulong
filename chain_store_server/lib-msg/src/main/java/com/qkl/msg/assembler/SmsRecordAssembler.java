package com.qkl.msg.assembler;


import com.qkl.msg.bean.SendStatus;
import com.qkl.msg.bean.SmsTemplate;
import com.qkl.msg.dto.SmsRecordDto;
import com.qkl.msg.entity.SmsRecord;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 6299 on 2018/5/31.
 * Modify by dengjihai on 2018/8/10.
 */
public class SmsRecordAssembler {

    public static SmsRecord convertToEntity(SmsRecordDto smsRecordDto, SmsRecord smsRecord){
        if(smsRecord==null){
            smsRecord = new SmsRecord();
        }
        smsRecord.setMobile(smsRecordDto.getMobile());
        smsRecord.setCode(smsRecordDto.getCode());
        smsRecord.setContent(smsRecordDto.getContent());
        smsRecord.setTemplateCode(SmsTemplate.getProperty(SmsTemplate.class,smsRecordDto.getTemplateCode()));
        smsRecord.setStatus(SendStatus.getProperty(SendStatus.class,smsRecordDto.getStatus()));
        return smsRecord;
    }


    public static SmsRecordDto convertToDto(SmsRecord smsRecord){
        if (null==smsRecord) return null;
        SmsRecordDto smsRecordDto = new SmsRecordDto();
        BeanUtils.copyProperties(smsRecord,smsRecordDto);
        smsRecordDto.setStatus(smsRecord.getStatus()==null?null:smsRecord.getStatus().getCode());
        smsRecordDto.setTemplateCode(smsRecord.getTemplateCode()==null?null:smsRecord.getTemplateCode().getCode());
        return smsRecordDto;
    }


    public static List<SmsRecordDto> convertToDtoList(List<SmsRecord> smsRecordList){
        List<SmsRecordDto> smsRecordDtoList= new ArrayList<>();
        smsRecordList.forEach(smsRecord -> smsRecordDtoList.add(convertToDto(smsRecord)) );
        return smsRecordDtoList;
    }

}
