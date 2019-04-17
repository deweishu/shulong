package com.qkl.admin.entity;

import com.google.common.collect.Sets;
import com.qkl.common.bean.BaseStatus;
import com.qkl.common.bean.UUIDEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Entity
@Table(name = "a_role")
public class Role extends UUIDEntity {

    private String name;

    private BaseStatus status;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RolePermission> permissions;

    public Role() {

    }

    public Role(String id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseStatus getStatus() {
        return status;
    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }

    public Set<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public Set<String> permissions() {
        Set<String> result = Sets.newHashSet();
        if (permissions == null || permissions.isEmpty()) {
            return result;
        }
        permissions.forEach(k -> result.add(k.getPermission()));
        return result;
    }
    
}
