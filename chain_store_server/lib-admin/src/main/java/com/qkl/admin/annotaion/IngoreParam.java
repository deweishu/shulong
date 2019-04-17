package com.qkl.admin.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略参数
 * Created by dengjihai on 2018/6/4.
 */
@Target(value = {ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IngoreParam {
    boolean value() default false;
}
