package com.qkl.common.web;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.CollectionUtil;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpMethodInfo {
    private static final Map<String, HttpMethodInfo> methodInfoMap = Maps.newHashMap();

    private boolean isAjax = false;

    private String ajaxRedirectReturnUri;

    private boolean needLogin = false;

    private Set<String> allowPermissions;

    private String method;

    public static HttpMethodInfo parse(HandlerMethod method) {
        HttpMethodInfo result = methodInfoMap.get(getMethodMapKey(method));
        if (result != null) {
            return result;
        }

        result = new HttpMethodInfo();
        AjaxMapping am = method.getMethodAnnotation(AjaxMapping.class);
        if (am != null) {
            result.isAjax = true;
            result.ajaxRedirectReturnUri = am.currentPageUri();
        }

        RequestPermission rp = method.getMethodAnnotation(RequestPermission.class);
        if (rp != null) {
            result.needLogin = true;

            if (rp.value().length > 0) {
                result.allowPermissions = Sets.newHashSet();
                Arrays.stream(rp.value()).forEach(result.allowPermissions::add);
            }
        }

        methodInfoMap.put(getMethodMapKey(method), result);
        return result;
    }

    private static String getMethodMapKey(HandlerMethod method) {
        return method.getBeanType().getName() + ":" + method.getMethod().getName();
    }

    public boolean isAjax() {
        return isAjax;
    }

    public String getAjaxRedirectReturnUri() {
        return ajaxRedirectReturnUri;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public boolean checkPermission(PermissionUser permissionUser) {
        if (allowPermissions == null) {
            return true;
        }

        for (String p : permissionUser.permissions()) {
            if (allowPermissions.contains(p)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkPermission(List<String> permissions) {
        if (CollectionUtil.isNil(allowPermissions)) {
            return true;
        }

        if (CollectionUtil.isNil(permissions)){
            return false;
        }

        for (String p : permissions) {
            if (allowPermissions.contains(p)) {
                return true;
            }
        }

        return false;
    }
}
