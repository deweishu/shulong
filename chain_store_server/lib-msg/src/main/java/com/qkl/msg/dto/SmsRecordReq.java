package com.qkl.msg.dto;

import com.qkl.common.dto.PageDto;

/**
 * Created by Benson on 2018/8/10.
 */
public class SmsRecordReq extends PageDto {

    private String mobile;

    private Integer templateCode;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(Integer templateCode) {
        this.templateCode = templateCode;
    }
}
