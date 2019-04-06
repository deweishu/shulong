package com.tensquare.qa;

import com.tensquare.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 应用的配置(注册过滤器, 监听器, filter...)
 */
@Component
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns: 匹配拦截的路径
        //excludePathPatterns: 不拦截什么路径
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(new String[]{"/**/login","/**/register"});
    }
}
