package com.qkl.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-08-29
 **/
@ApiModel(description = "修改密码请求实体类")
public class UpdatePwdReq implements Serializable {

    @ApiModelProperty(name = "手机号，找回密码的时候必填",notes = "手机号，找回密码的时候必填")
    private String phone;

    @ApiModelProperty(name = "旧密码，加密后的值，修改密码的时候必填",notes = "旧密码，加密后的值，修改密码的时候必填")
    private String oldPass;

    @ApiModelProperty(name = "验证码，找回密码的时候必填",notes = "验证码，找回密码的时候必填")
    private String code;

    @ApiModelProperty(name = "新密码，加密后的值",notes = "新密码，加密后的值")
    @NotEmpty(message = "新密码不能为空")
    private String newPass;

    @ApiModelProperty(name = "确认新密码，加密后的值",notes = "确认新密码，加密后的值")
    @NotEmpty(message = "确认新密码不能为空")
    private String newConfirmPass;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewConfirmPass() {
        return newConfirmPass;
    }

    public void setNewConfirmPass(String newConfirmPass) {
        this.newConfirmPass = newConfirmPass;
    }
}
