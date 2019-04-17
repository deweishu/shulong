package com.qkl.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qkl.common.bean.UUIDEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Entity
@Table(name = "a_role_permission")
public class RolePermission extends UUIDEntity {

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    private String permission;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
