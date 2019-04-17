package com.qkl.msg.dto;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-10-19
 **/
public class SendPushDto implements Serializable {

    private String title;

    private String text;

    private String content;

    private Integer pushType;

    private String operateId;

    private String operateName;

    private Boolean ring;//是否响铃

    private Boolean vibrate;//是否振动

    private String apkId;//打开应用的id

    public String getApkId() {
        return apkId;
    }

    public void setApkId(String apkId) {
        this.apkId = apkId;
    }

    public Boolean getRing() {
        return ring;
    }

    public void setRing(Boolean ring) {
        this.ring = ring;
    }

    public Boolean getVibrate() {
        return vibrate;
    }

    public void setVibrate(Boolean vibrate) {
        this.vibrate = vibrate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }
}
