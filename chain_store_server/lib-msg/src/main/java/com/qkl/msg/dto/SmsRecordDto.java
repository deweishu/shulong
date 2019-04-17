package com.qkl.msg.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;

/**
 * Created by 6299 on 2018/5/31.
 * Modify by dengjihai on 2018/8/10.
 */
public class SmsRecordDto extends Dto {

    private String id;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**模板**/
    private Integer templateCode;

    /**手机号码**/
    private String mobile;

    /**验证码**/
    private String code;

    /**发送内容**/
    private String content;

    /**发送状态：10-已发送 20-发送成功 30-发送失败**/
    private Integer status;

    public SmsRecordDto() {
    }

    public SmsRecordDto(Integer templateCode, String mobile, String code, String content) {
        this.templateCode = templateCode;
        this.mobile = mobile;
        this.code = code;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(Integer templateCode) {
        this.templateCode = templateCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
