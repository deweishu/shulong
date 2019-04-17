package com.qkl.common.util;

import com.qkl.common.dto.PageResponse;
import com.qkl.common.web.*;
import com.qkl.common.web.session.SingleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class WebUtil {

    static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public final static String REDIRECT_RETURN_URL = "redirectReturnUrl";

    public final static String OPEN_ID = "openid"; // 微信的openid 和 支付宝的user_id

    public final static String HTTP_PREFIX = "http"; // http前缀

    public final static String GET = "GET"; // GET方法

    /**
     * ajax请求重定向
     *
     * @param response
     * @param redirect
     */
    public static void ajaxRedirect(HttpServletResponse response, String redirect) {
        try {
            OutputStream outputStream = response.getOutputStream();
            response.setHeader("content-type", "text/html;charset=UTF-8");
            redirect = String.format("{\"redirect\": \"%s\"}", redirect);
            outputStream.write(redirect.getBytes("UTF-8"));
        } catch (Exception e) {
            logger.error("ajax request redirect error", e);
        }
    }

    /**
     * 普通请求重定向
     *
     * @param response
     * @param redirect
     * @throws Exception
     */
    public static void redirect(HttpServletResponse response, String redirect) throws Exception {
        if (RequestContext.getHttpMethodInfo().isAjax()) {
            ajaxRedirect(response, redirect);
        } else {
            response.sendRedirect(redirect);
        }
    }

    /**
     * 根据本应用的域名去重定向
     * @param request
     * @param response
     * @param redirect
     * @throws Exception
     */
    public static void redirectByDomain(HttpServletRequest request, HttpServletResponse response, String redirect) throws Exception {
        String domain = getDomainBasePath() + "/";

        if (RequestContext.getHttpMethodInfo().isAjax()) {
            ajaxRedirect(response, domain + redirect);
        } else {
            response.sendRedirect(domain + redirect);
        }
    }

    public static JsonResponse jsonResponse(ResponseHeader header, Object data) {
        return JsonResponse.newInstance(header, data);
    }

    public static JsonResponse jsonResponse(ResponseHeader header) {
        return jsonResponse(header, null);
    }

    public static JsonResponse successJsonResponse(String successMessage, Object data) {
        return JsonResponse.newInstance(StandardResponseHeader.SUCCESS, successMessage, data);
    }

    public static JsonResponse successJsonResponse(String 获取APP信息成功, PageResponse apkList) {
        return successJsonResponse(null);
    }

    public static JsonResponse successJsonResponse(String successMessage) {
        return successJsonResponse(successMessage, null);
    }

    public static JsonResponse errorJsonResponse(String errorMessage,Object data){
        return JsonResponse.newInstance(StandardResponseHeader.ERROR,errorMessage,data);
    }

    public static JsonResponse errorJsonResponse(ResponseHeader responseHeader,Object data){
        return JsonResponse.newInstance(responseHeader,responseHeader.getDescription(),data);
    }

    public static JsonResponse errorJsonResponse(String errorMessage){
        return errorJsonResponse(errorMessage,null);
    }

    public static JsonResponse errorJsonResponse(ResponseHeader responseHeader){
        return errorJsonResponse(responseHeader,null);
    }

    /**
     * 自定义错误消息
     * @param message
     * @param responseHeader
     * @return
     */
    public static JsonResponse errorJsonResponseCustomMsg(String message, ResponseHeader responseHeader) {
        return JsonResponse.newInstance(responseHeader,message,null);
    }

    public static JsonResponse tokenFail(String errorMessage){
        return JsonResponse.newInstance(StandardResponseHeader.ERROR_TOCKEN_EXPIRE,errorMessage,null);
    }

    public static JsonResponse tokenError(String errorMessage){
        return JsonResponse.newInstance(StandardResponseHeader.ERROR_TOCKEN,errorMessage,null);
    }

    public static JsonResponse registered(String errorMessage){
        return JsonResponse.newInstance(StandardResponseHeader.USER_REGISTERED,errorMessage,null);
    }


    /**
     * 获取服务器带域名的基础路径
     * 例如：http://cpsydltest.cunpiao.com
     * @return
     */
    public static String getDomainBasePath() {
        return RequestContext.getModuleProperties().domain;
    }

    /**
     * 设置当前访问URL
     * @param accessUrlCache
     * @param sessionId SingleSession的sessionId
     * @param request
     */
    public static void setAccessUrl(AccessUrlCache accessUrlCache, String sessionId, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(request.getRequestURI());
        if (StringUtil.isNotNil(sb.toString()) && !sb.toString().startsWith(HTTP_PREFIX)) {
            sb.insert(0, WebUtil.getDomainBasePath());
        }
        if (request.getQueryString() != null) {
            sb.append("?" + request.getQueryString());
        }
        String accessUrl = sb.toString();

        accessUrlCache.setAccessUrl(sessionId, accessUrl);
    }

    /**
     * 获取前面setAccessUrl()设置的访问URL
     * @param accessUrlCache
     * @param sessionId SingleSession的sessionId
     * @return
     */
    public static String getAccessUrl(AccessUrlCache accessUrlCache, String sessionId) {
        return accessUrlCache.getAccessUrl(sessionId);
    }

    public static void setRedirectReturnUrl(SingleSession session, HttpServletRequest request) {
        if (RequestContext.getHttpMethodInfo().isAjax()) {
            session.put(REDIRECT_RETURN_URL, RequestContext.getModuleProperties().domain + RequestContext.getHttpMethodInfo().getAjaxRedirectReturnUri());
        }

        StringBuilder sb = new StringBuilder(request.getRequestURI());
        if (StringUtil.isNotNil(sb.toString()) && !sb.toString().startsWith(HTTP_PREFIX)) {
            sb.insert(0, getDomainBasePath());
        }
        if (request.getQueryString() != null) {
            sb.append("?" + request.getQueryString());
        }
        session.put(REDIRECT_RETURN_URL, sb.toString());
    }

    public final static String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = "";
        try {
            ip = request.getHeader("X-Forwarded-For");
            logger.debug("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                    logger.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                    logger.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    logger.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                    logger.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    logger.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = (String) ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return StringUtil.isBlank(ip) ? "127.0.0.1" : ip.trim();
    }

}
