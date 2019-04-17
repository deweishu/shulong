package com.qkl.api.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "查询app明细请求实体类")
public class AppInfoReq implements Serializable {


    @ApiModelProperty(name = "应用ID，必填",notes = "应用id，必填")
    @NotEmpty(message = "应用ID不能为空")
    private String appId;

    @ApiModelProperty(notes = "客户端类型（10.安卓20.IOS）",name = "客户端类型（10.安卓20.IOS）")
    @NotNull(message = "客户端类型不能为空")
    private Integer clientType;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }
}
