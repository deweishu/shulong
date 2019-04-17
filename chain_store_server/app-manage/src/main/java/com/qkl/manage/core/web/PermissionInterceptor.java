package com.qkl.manage.core.web;

import com.qkl.admin.entity.Admin;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.AccessUrlCache;
import com.qkl.common.web.RequestContext;
import com.qkl.manage.core.cache.AdminSingleSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccessUrlCache accessUrlCache;

    private AdminSingleSession singleSession;

    @Autowired
    public PermissionInterceptor(AdminSingleSession singleSession) {
        this.singleSession = singleSession;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (singleSession.getUser() == null) {
            WebUtil.setAccessUrl(accessUrlCache, singleSession.getSessionId(), request);
            WebUtil.setRedirectReturnUrl(singleSession, request);
            WebUtil.redirectByDomain(request, response, RequestContext.getModuleProperties().loginUri);
            return false;
        }

        if (!RequestContext.getHttpMethodInfo().checkPermission(singleSession.getUser())) {
            WebUtil.redirectByDomain(request, response, RequestContext.getModuleProperties().forbiddenUri);
            return false;
        }
        Admin admin = singleSession.getUser();
        request.setAttribute("user", admin);
        return true;
    }

}
