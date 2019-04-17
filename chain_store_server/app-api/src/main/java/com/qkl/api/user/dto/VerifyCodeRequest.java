package com.qkl.api.user.dto;

import com.qkl.common.dto.Dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 短信验证码请求类
 * Created by dengjihai on 2017/10/25.
 */
@ApiModel(value = "短信验证码校验")
public class VerifyCodeRequest extends Dto {

    @ApiModelProperty(value = "手机号，必填", name = "mobile", required = true)
    @Pattern(regexp = "^1[0-9]{10}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号不能为空")
    private String mobile;  // 手机号码

    @ApiModelProperty(value = "验证码，必填", name = "code", required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;    // 验证码


    public VerifyCodeRequest() {
    }

    public VerifyCodeRequest(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
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

}
