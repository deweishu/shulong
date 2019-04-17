package com.qkl.common.bean;

import java.lang.annotation.*;

/**
 * 字符串属性枚举类
 * Created by dengjihai on 2018/3/28.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StringProperty {
    String code();

    String name();
}
