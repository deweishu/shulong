package com.qkl.menu;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 菜单定义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MenuType {
}
