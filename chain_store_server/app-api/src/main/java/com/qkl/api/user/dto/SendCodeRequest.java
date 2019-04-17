package com.qkl.api.user.dto;

import com.qkl.common.dto.Dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 发送短信验证码
 * Created by Benson on 2017/10/25.
 */
@ApiModel(value = "发送短信验证码")
public class SendCodeRequest extends Dto {

    @ApiModelProperty(value = "手机号，必填", name = "mobile", required = true)
    @Pattern(regexp = "^1[0-9]{10}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号不能为空")
    private String mobile;  // 手机号码

    @ApiModelProperty(value = "短信模板类型：10-短信，20-语音；选填，默认10", name = "moduleType", required = true)
    private Integer moduleType;   // 类型


    private String geetest_challenge;

    private String fn_geetest_validate;


    private String fn_geetest_seccode;

    private String imgCode;

    private String invetCode;

    public String getInvetCode() {
        return invetCode;
    }

    public void setInvetCode(String invetCode) {
        this.invetCode = invetCode;
    }

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getFn_geetest_validate() {
        return fn_geetest_validate;
    }

    public void setFn_geetest_validate(String fn_geetest_validate) {
        this.fn_geetest_validate = fn_geetest_validate;
    }

    public String getFn_geetest_seccode() {
        return fn_geetest_seccode;
    }

    public void setFn_geetest_seccode(String fn_geetest_seccode) {
        this.fn_geetest_seccode = fn_geetest_seccode;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }
}
