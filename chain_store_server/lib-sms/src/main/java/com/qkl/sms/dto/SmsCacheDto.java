package com.qkl.sms.dto;

import com.qkl.common.dto.Dto;

import java.util.Date;

/**
 * 验证码缓存实体封装类
 * Created by dengjihai on 2018/5/15.
 */
public class SmsCacheDto extends Dto {

    private String smsCode;//发送code

    private Date sendTime;//发送时间


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "SmsCacheDto{" +
                "smsCode='" + smsCode + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
