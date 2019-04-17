package com.qkl.api.user.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-09-10
 **/
public class UpdateHeadImgReq implements Serializable {

    @NotEmpty(message = "头像地址不能为空")
    private String headImg;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
