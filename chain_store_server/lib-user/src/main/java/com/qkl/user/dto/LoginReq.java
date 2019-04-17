package com.qkl.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-08-24
 **/
@ApiModel(description = "登录/注册请求实体类")
public class LoginReq implements Serializable {

    @ApiModelProperty(name = "登录手机号",notes = "手机号")
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(notes = "验证码，注册接口必填",name = "验证码，注册接口必填")
    private String code;

    @ApiModelProperty(notes = "登录密码，md5加密",name = "登录密码,md5加密")
    @NotEmpty(message = "登录密码不能为空")
    private String passWord;

    @ApiModelProperty(notes = "邀请码",name = "邀请码")
    private String invetCode;

    @ApiModelProperty(notes = "个推客户端ID",name = "个推客户端ID")
    private String gtClientId;

    public String getGtClientId() {
        return gtClientId;
    }

    public void setGtClientId(String gtClientId) {
        this.gtClientId = gtClientId;
    }

    public String getInvetCode() {
        return invetCode;
    }

    public void setInvetCode(String invetCode) {
        this.invetCode = invetCode;
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
