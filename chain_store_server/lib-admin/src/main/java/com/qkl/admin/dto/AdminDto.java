package com.qkl.admin.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;

/**
 * Created by dengjihai on 2018/3/9.
 */
public class AdminDto extends Dto {

    private String id;

    private String username;

    private String email;

    private String password;

    private String mobile;

    private String realname;

    private Integer status;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /*private Set<RoleDto> roles;*/


    public AdminDto() {
    }

    public AdminDto(String id, String username, String email, String password, String mobile, String realname, Integer status, Date createTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.realname = realname;
        this.status = status;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /*public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }*/
}
