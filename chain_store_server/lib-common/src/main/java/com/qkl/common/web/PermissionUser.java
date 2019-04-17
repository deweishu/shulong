package com.qkl.common.web;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Set;

@MappedSuperclass
public interface PermissionUser extends Serializable {
    Set<String> permissions();
}
