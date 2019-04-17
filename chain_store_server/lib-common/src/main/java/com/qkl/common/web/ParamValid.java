package com.qkl.common.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by niejiuqian on 2017/9/13.
 * 请求参数校验注解
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamValid {
    /**
     * 非空校验
     * @return
     */
    boolean notEmpty() default false;

    String notEmptyErrMsg() default "";

    /**
     * 字符长度校验
     * @return
     */
    int maxLen() default Integer.MAX_VALUE;

    String maxLenErrMsg() default "";


    /**
     * 字符长度校验
     * @return
     */
    int minLen() default -1;

    String minLenErrMsg() default "";
    /**
     * 手机号码正则表达式校验
     * @return
     */
    String regex() default "";

    String regexErrMsg() default "格式错误";

}
