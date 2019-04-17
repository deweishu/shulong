package com.qkl.common.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDefiner {
    String name();

    String group();

    RoleSpecification[] role() default {RoleSpecification.MODIFIABLE}; //默认可修改
}
