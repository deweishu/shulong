package com.qkl.api.user.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-09-10
 **/
public class UpdateNickReq implements Serializable {

    @NotEmpty(message = "昵称不能为空")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
