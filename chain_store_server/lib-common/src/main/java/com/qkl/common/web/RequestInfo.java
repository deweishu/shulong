package com.qkl.common.web;

import com.qkl.common.util.StringUtil;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

public class RequestInfo {
    private RequestSource requestSource;

    private HttpMethod requestHttpMethod;

    public static RequestInfo parse(HttpServletRequest request) {
        RequestInfo result = new RequestInfo();

        if (isFromWeixin(request)) {
            result.requestSource = RequestSource.WEIXIN;
        } else {
            result.requestSource = RequestSource.UNKNOWN;
        }

        result.requestHttpMethod = HttpMethod.resolve(request.getMethod().toUpperCase());

        return result;
    }

    private static boolean isFromWeixin(HttpServletRequest request) {
        if(StringUtil.isNil(request.getHeader("user-agent"))){
            return false;
        }
        if (request.getHeader("user-agent").toLowerCase().indexOf("micromessenger") > 0) {
            return true;
        } else if (request.getHeader("wxuserAgent") != null) {
            return true;
        }
        return false;
    }

    public RequestSource getRequestSource() {
        return requestSource;
    }

    public HttpMethod getRequestHttpMethod() {
        return requestHttpMethod;
    }

    public enum RequestSource {
        /**
         * 微信
         */
        WEIXIN,
        /**
         * 未知来源
         */
        UNKNOWN
    }
}
