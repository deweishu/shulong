package com.qkl.admin.dto;
import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;

public class WalletReq extends Dto {


    /**主键**/
    private String id;

    /**创建时间**/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

    /**更新时间**/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;

    /** 名称*/
    private  String name;

    /** 介绍**/
    private  String indruction;

    /** 图标 **/
    private  String image;

    /** 规则 **/
    private  String rule;


    /** 地址 **/
    private  String address;


    /** 手续费 **/
    private  Integer fee;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndruction() {
        return indruction;
    }

    public void setIndruction(String indruction) {
        this.indruction = indruction;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
