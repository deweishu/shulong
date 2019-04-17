package com.qkl.common.permission;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PermissionType {
}
