package com.qkl.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-08-24
 **/
@ApiModel(description = "登录返回实体类")
public class UserLoginResp implements Serializable {



    private String id;

    @ApiModelProperty(notes = "手机号",name = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "用户名",name = "用户名")
    private String nickName;

    @ApiModelProperty(name = "头像url",notes = "头像url")
    private String headImg;

    @ApiModelProperty(name = "糖果余额",notes = "糖果余额")
    private Integer candyAmount;

    @ApiModelProperty(name = "令牌",notes = "令牌")
    private String token;

    @ApiModelProperty(name = "邀请码",notes = "邀请码")
    private String invetCode;

    public String getInvetCode() {
        return invetCode;
    }

    public void setInvetCode(String invetCode) {
        this.invetCode = invetCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getCandyAmount() {
        return candyAmount;
    }

    public void setCandyAmount(Integer candyAmount) {
        this.candyAmount = candyAmount;
    }
}
