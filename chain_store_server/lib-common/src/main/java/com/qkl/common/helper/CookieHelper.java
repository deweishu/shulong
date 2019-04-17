package com.qkl.common.helper;

import com.qkl.common.web.RequestContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 帮助工具类
 */
public class CookieHelper {

    public static void addNewCookie(HttpServletResponse response, String key, String value) {
        addCookie(response, createCookie(key, value));
    }

    public static Cookie createCookie(String key, String value) {
        Cookie c = new Cookie(key, value);
        c.setDomain(RequestContext.getModuleProperties().cookieDomain);
        c.setPath("/");
        return c;
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    public static Cookie[] getCookies(HttpServletRequest request) {
        return request.getCookies();
    }

    public static Cookie getCookie(HttpServletRequest request, String key) {
        Cookie[] cs = getCookies(request);
        if (StringUtils.isEmpty(key) || cs == null) {
            return null;
        }
        for (Cookie c : cs) {
            if (key.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie c = getCookie(request, key);
        if (c != null) {
            return c.getValue();
        }
        return null;
    }
}
