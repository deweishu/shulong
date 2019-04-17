package com.qkl.common.web;

import com.qkl.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by niejiuqian on 2017/10/19.
 * 请求参数打印
 */
@Component
public class LogParamInterceptor extends HandlerInterceptorAdapter {
    public static final Logger logger = LoggerFactory.getLogger(LogParamInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String body = null;
        if (request instanceof MyRequestWrapper) {
            MyRequestWrapper requestWrapper = (MyRequestWrapper) request;
            body = requestWrapper.getStringBody();
        }
        StringBuffer uri = request.getRequestURL();
        logger.info("####请求IP：{}", StringUtil.getIpAddr(request));
        logger.info("####客户端请求url：{}",uri.toString());
        logger.info("####客户端请求header：{}",headerToString(request));
        logger.info("####客户端请求body：{}",body);
        //return super.preHandle(request, response, handler);
        return true;
    }


    public String headerToString(HttpServletRequest request){
        StringBuilder sb = new StringBuilder("\n");
        Enumeration<String> henderNames = request.getHeaderNames();
        while (henderNames.hasMoreElements()){
            String headerName = henderNames.nextElement();
            String value = request.getHeader(headerName);
            sb.append(headerName).append("=").append(value).append("\n");
        }
        return sb.toString();
    }
}
