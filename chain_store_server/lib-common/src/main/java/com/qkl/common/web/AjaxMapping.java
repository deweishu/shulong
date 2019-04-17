package com.qkl.common.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AjaxMapping {
    /**
     * @return ajax请求过程中如果发生异常（如未登录），被重定向至其他页面进行异常处理，当解决后用户将返回此URI继续业务流程
     */
    String currentPageUri();
}
