package com.qkl.common.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModuleProperties {

    /**
     * 模块
     */
    @Value("${module.code}")
    public String module;

    /**
     * 模块编码
     */
    @Value("${module.prefix}")
    public String prefix;

    /**
     * 模块主域
     */
    @Value("${module.domain.main}")
    public String domain;

    /**
     * cookie域
     */
    @Value("${module.domain.cookie}")
    public String cookieDomain;

    /**
     * 静态文件域
     */
    @Value("${module.domain.static}")
    public String staticDomain;

    /**
     * 登录路径
     */
    @Value("${module.uri.login}")
    public String loginUri;

    /**
     * 403错误路径
     */
    @Value("${module.uri.forbidden}")
    public String forbiddenUri;

    /**
     * 404错误路径
     */
    @Value("${module.uri.notFound}")
    public String notFoundUri;

    /**
     * 强制退出路径
     */
    @Value("${module.uri.forceExit}")
    public String forceExitUri;

    /**
     * 404错误页面
     */
    @Value("${module.page.notFound}")
    public String notFoundPage;

    /**
     * 500错误页面
     */
    @Value("${module.page.error}")
    public String errorPage;

    /**
     * 404错误ajax页面
     */
    @Value("${module.ajax.notFound}")
    public String notFoundAjax;

    /**
     * 500错误ajax页面
     */
    @Value("${module.ajax.error}")
    public String errorAjax;
}
