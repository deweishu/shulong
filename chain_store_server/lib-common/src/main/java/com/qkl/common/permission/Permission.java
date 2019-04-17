package com.qkl.common.permission;

import java.io.Serializable;

public class Permission implements Serializable {
    private String code;

    private String name;

    public Permission(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
