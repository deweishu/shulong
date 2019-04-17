package com.qkl.common.permission;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class PermissionGroup implements Serializable {
    private String code;

    private String name;

    private List<Permission> permissions;

    public PermissionGroup(String code, String name) {
        this.code = code;
        this.name = name;
        this.permissions = Lists.newArrayList();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
