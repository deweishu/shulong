package com.qkl.api.auth.bean;

import com.qkl.api.WebConfig;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by dengjihai on 2018/3/1.
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    public final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private TokenSessionCache tokenSessionCache;

    @Value(value = "${spring.profiles.active}")
    String springProfilesActive;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestToken = request.getHeader("token");
        response.setCharacterEncoding("UTF-8");
        HalfOpen halfOpen = null;
        if (null!= handler && handler instanceof HandlerMethod) {
            halfOpen = ((HandlerMethod) handler).getMethod().getAnnotation(HalfOpen.class);
        }


        // token校验
        if (validUrl(request)) {
            // 如果是半开放接口，并且token为空，就放行
            if (null != halfOpen && StringUtil.isBlank(requestToken)) {
                return true;
            }

            if(StringUtil.isNil(requestToken)){
                ResponseHelper.write(response, JsonUtil.beanToJson(WebUtil.tokenError("令牌为空，请重新登录")));
                return false;
            }

            boolean result = tokenSessionCache.checkToken(requestToken);
            if(!result){ //已超时，须重新登录
                ResponseHelper.write(response,JsonUtil.beanToJson(WebUtil.tokenFail("令牌已过期，请重新登录")));
                return false;
            }
        }

        request.setAttribute("startTime",System.currentTimeMillis());
        return true;
    }

    private boolean validUrl(HttpServletRequest request) {
        logger.info("================>>>>springProfilesActive={}",springProfilesActive);
        if (StringUtil.isBlank(springProfilesActive)){
            return true;
        }
        String reqUrl = request.getRequestURI();
        for (int i = 0; i < WebConfig.globalFunction.length; i++) {
            String url = WebConfig.globalFunction[i];
            if (reqUrl.contains(url)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        //long endTime = System.currentTimeMillis();
        //long startTime = (Long)request.getAttribute("startTime");
        //long executeTime = endTime - startTime;
        //logger.debug("[" + handler + "] \n 执行耗时 : " + executeTime + "ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        tokenSessionCache.clearLocal();
    }
}
