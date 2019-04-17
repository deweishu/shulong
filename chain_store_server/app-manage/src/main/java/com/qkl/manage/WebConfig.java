package com.qkl.manage;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.qkl.common.web.PerformanceInterceptor;
import com.qkl.common.web.RequestContext;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.manage.core.web.ConsoleLogInterceptor;
import com.qkl.manage.core.web.ConsoleMenuInterceptor;
import com.qkl.manage.core.web.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;


@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final String[] globalFunction = {
            "/login",
            "/admin/hello",
            "/admin/register",
            "/sms/code",
            "/sms/code/validate",
            "/test/test",
            "/test/pushTest"
    };


    @Autowired
    private PerformanceInterceptor performanceInterceptor;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private AdminSingleSession adminSingleSession;
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    @Autowired
    private ConsoleMenuInterceptor consoleMenuInterceptor;

    /*操作日志拦截器*/
    @Autowired
    private ConsoleLogInterceptor consoleLogInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        addAll(registry, requestContext);
        addAll(registry, performanceInterceptor);
        addAll(registry, adminSingleSession);
        addAllExceptGlobalPage(registry, permissionInterceptor);
        addAllExceptGlobalInfoPage(registry, consoleLogInterceptor);
        addAllExceptGlobalPage(registry, consoleMenuInterceptor);
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


    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }


    /**
     * fastJson相关设置
     */
    private FastJsonConfig getFastJsonConfig() {

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 在serializerFeatureList中添加转换规则
        List<SerializerFeature> serializerFeatureList = new ArrayList<SerializerFeature>();
        serializerFeatureList.add(SerializerFeature.PrettyFormat);
        serializerFeatureList.add(SerializerFeature.WriteMapNullValue);
        serializerFeatureList.add(SerializerFeature.WriteNullStringAsEmpty);
        serializerFeatureList.add(SerializerFeature.WriteNullListAsEmpty);
        serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
        SerializerFeature[] serializerFeatures = serializerFeatureList.toArray(new SerializerFeature[serializerFeatureList.size()]);
        fastJsonConfig.setSerializerFeatures(serializerFeatures);

        return fastJsonConfig;
    }

    /**
     * fastJson相关设置
     */
    private FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {

        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter4();

        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
        supportedMediaTypes.add(MediaType.parseMediaType("application/json"));

        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(getFastJsonConfig());

        return fastJsonHttpMessageConverter;
    }

    /**
     * 添加fastJsonHttpMessageConverter到converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }
}
