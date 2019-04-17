/**
 *
 */
package com.qkl.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截器，设置当前请求类型、来源、权限等信息
 */
@Component
public class RequestContext extends HandlerInterceptorAdapter {

    private static final ThreadLocal<ModuleProperties> threadModuleProperties = new ThreadLocal<>();

    private static final ThreadLocal<RequestInfo> threadRequestInfo = new ThreadLocal<>();

    private static final ThreadLocal<HttpMethodInfo> threadHttpMethodInfo = new ThreadLocal<>();

    @Autowired
    private ModuleProperties moduleProperties;

    public static RequestInfo getRequestInfo() {
        return threadRequestInfo.get();
    }

    public static HttpMethodInfo getHttpMethodInfo() {
        return threadHttpMethodInfo.get();
    }

    public static ModuleProperties getModuleProperties() {
        return threadModuleProperties.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 跨域访问CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type,token,security-token,origin,x-requested-with,content-type,x-http-method-override,accept");

        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            response.setStatus(200);
            return true;
        }

        resourcesContext(request);

        threadRequestInfo.set(RequestInfo.parse(request));
        threadHttpMethodInfo.set(HttpMethodInfo.parse((HandlerMethod) handler));

        if (threadModuleProperties.get() == null) {
            threadModuleProperties.set(moduleProperties);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {// 设置session超时
        threadRequestInfo.remove();
        threadHttpMethodInfo.remove();
    }

    private void resourcesContext(HttpServletRequest request) {
        request.setAttribute("domain", moduleProperties.domain);
        request.setAttribute("static", moduleProperties.staticDomain);
        request.setAttribute("ctx", request.getContextPath());
    }
}
