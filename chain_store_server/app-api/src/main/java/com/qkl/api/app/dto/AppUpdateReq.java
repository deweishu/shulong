package com.qkl.api.app.dto;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AppUpdateReq implements Serializable {

    @NotEmpty(message = "当前版本不能为空")
    private String versionName;

    @NotNull(message = "客户端类型不能为空")
    private Integer clientType;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }
}
