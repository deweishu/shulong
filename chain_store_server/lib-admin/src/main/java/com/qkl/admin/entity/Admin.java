package com.qkl.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.qkl.common.bean.BaseStatus;
import com.qkl.common.bean.UUIDEntity;
import com.qkl.common.web.PermissionUser;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Entity
@Table(name = "a_admin")
public class Admin extends UUIDEntity implements PermissionUser {

    private String username;    // 用户名

    private String email;       // 邮件

    private String password;    // 登录密码

    private String mobile;      // 手机号码

    private String realname;    // 姓名

    private BaseStatus status;  // 状态

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "a_admin_role",
            joinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName ="id")})
    private Set<Role> roles = new HashSet<Role>();

    public Admin() {
        super();
    }

    public Admin(String username) {
        super();
        this.username = username;
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

    @JsonIgnore
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

    public BaseStatus getStatus() {
        return status;
    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Set<String> permissions() {
        Set<String> result = Sets.newHashSet();
        if (roles == null) {
            return result;
        }
        roles.forEach(k -> result.addAll(k.permissions()));
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
