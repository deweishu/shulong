package com.qkl.background;


import com.qkl.common.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.util.List;


public class WebConfig extends WebMvcConfigurerAdapter {

    public static final String[] globalFunction = {
            "/user/mobile/reg",
            "/user/mobile/login",
            "/swagger-resources",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/v2/api-docs",
            "/sms/code",
            "/sms/code/validate",
            "/hotsearch/get/all",
            "/common/getParams",
            "/banner/list",
            "/user/pwd/find",
            "/app/list",
            "/app/info",
            "/comment/log/list",
            "/",
            "/error"
    };


    @Autowired
    private PerformanceInterceptor performanceInterceptor;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private LogParamInterceptor logParamInterceptor;

    //出参封装处理
    @Autowired
    private ResponseBodyWrapperHandler responseBodyWrapperHandler;
    //全局自定义异常处理
    @Autowired
    private AppExceptionHandlerResolver appExceptionHandlerResolver;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置跨域映射
        registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        addAll(registry, requestContext);
        addAll(registry, performanceInterceptor);
        addAll(registry, logParamInterceptor);
    }


    private void addFilterGlobalPage(InterceptorRegistry registry, HandlerInterceptor adapter, String[] globals){
        InterceptorRegistration registration = registry.addInterceptor(adapter);
        registration.addPathPatterns(globals);
    }

    private InterceptorRegistration addAll(InterceptorRegistry registry, HandlerInterceptor adapter) {
        return registry.addInterceptor(adapter).addPathPatterns("/**");
    }

    private InterceptorRegistration addAllExceptGlobalPage(InterceptorRegistry registry, HandlerInterceptor adapter) {
        InterceptorRegistration result = addAll(registry, adapter);
        exceptPage(result, globalFunction);
        return result;
    }

    private InterceptorRegistration addAllExceptGlobalInfoPage(InterceptorRegistry registry, HandlerInterceptor adapter) {
        InterceptorRegistration result = addAll(registry, adapter);

        return result;
    }

    private InterceptorRegistration addAllExceptGlobalFunctionPage(InterceptorRegistry registry, HandlerInterceptor adapter) {
        InterceptorRegistration result = addAll(registry, adapter);
        exceptPage(result, globalFunction);
        return result;
    }

    private InterceptorRegistration exceptPage(InterceptorRegistration registration, String[] except) {
        for (String e : except) {
            registration.excludePathPatterns(e);
        }
        return registration;
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(responseBodyWrapperHandler);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(responseBodyWrapperHandler);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
        exceptionResolvers.add(appExceptionHandlerResolver);
    }

    @Bean
    public FilterRegistrationBean requstBodyReadFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        RequestBodyReadFilter signValidateFilter = new RequestBodyReadFilter();
        registration.setFilter(signValidateFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("10240KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
