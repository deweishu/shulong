package com.qkl.msg.entity;

import com.qkl.common.bean.UUIDEntity;
import com.qkl.msg.bean.SendStatus;
import com.qkl.msg.bean.SmsTemplate;

import javax.persistence.*;

/**
 * 短信发送记录
 * Created by Benson on 2018/8/10.
 */
@Entity
@Table(name = "m_sms_record")
public class SmsRecord extends UUIDEntity {

    /**模板**/
    @Column(name = "template_code")
    private SmsTemplate templateCode;

    /**手机号码**/
    @Column(name = "mobile")
    private String mobile;

    /**验证码**/
    @Column(name = "code")
    private String code;

    /**发送内容**/
    @Column(name = "content")
    private String content;

    /**发送状态：10-已发送 20-发送成功 30-发送失败**/
    @Column(name = "status")
    private SendStatus status;

    public SmsRecord() {

    }

    public SmsRecord(String id) {
        super.setId(id);
    }

    public SmsRecord(SmsTemplate templateCode, String mobile, String code, String content, SendStatus status) {
        this.templateCode = templateCode;
        this.mobile = mobile;
        this.code = code;
        this.content = content;
        this.status = status;
    }


    public SmsTemplate getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(SmsTemplate templateCode) {
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

    public SendStatus getStatus() {
        return status;
    }

    public void setStatus(SendStatus status) {
        this.status = status;
    }
}
