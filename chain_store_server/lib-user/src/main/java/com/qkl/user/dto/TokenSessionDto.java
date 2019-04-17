package com.qkl.user.dto;

import com.qkl.common.dto.Dto;

/**
 * Token会话信息
 * Created by dengjihai on 2018/3/1.
 */
public class TokenSessionDto extends Dto {

    // 令牌信息
    private String token;

    // 用户信息
    private UserLoginResp userLoginResp;


    public TokenSessionDto() {
    }

    public TokenSessionDto(String token, UserLoginResp userLoginResp) {
        this.token = token;
        this.userLoginResp = userLoginResp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLoginResp getUserLoginResp() {
        return userLoginResp;
    }

    public void setUserLoginResp(UserLoginResp userLoginResp) {
        this.userLoginResp = userLoginResp;
    }
}
