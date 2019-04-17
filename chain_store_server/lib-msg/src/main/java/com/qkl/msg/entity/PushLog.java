package com.qkl.msg.entity;

import com.qkl.common.bean.UUIDEntity;
import com.qkl.msg.bean.PushType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 推送日志记录
 * @author dengjihai
 * @create 2018-10-19
 **/
@Entity
@Table(name = "m_push_log")
public class PushLog extends UUIDEntity {


    /**推送通知栏的标题*/
    @Column(name = "push_title")
    private String pushTitle;

    /**推送通知栏的描述*/
    @Column(name = "push_txt")
    private String pushTxt;

    /**推送内容（透传消息）*/
    @Column(name = "push_content")
    private String pushContent;

    /**操作者id*/
    @Column(name = "operate_id")
    private String operateId;

    /**操作者名称*/
    @Column(name = "operate_name")
    private String operateName;

    /***10.单个推送20.群推*/
    @Column(name = "push_type")
    private PushType pushType;

    @Column(name = "push_result")
    private String pushResult;

    public String getPushResult() {
        return pushResult;
    }

    public void setPushResult(String pushResult) {
        this.pushResult = pushResult;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushTxt() {
        return pushTxt;
    }

    public void setPushTxt(String pushTxt) {
        this.pushTxt = pushTxt;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
    }
}
