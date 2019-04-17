package com.qkl.common.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qkl.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;

public class CommonApplication extends SpringBootServletInitializer {

    @Autowired
    private ModuleProperties moduleProperties;

    @Bean
    @Scope("prototype")
    RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtil.getMapper();
    }

    @Bean
    public Filter characterEncodingFilter() {
        OrderedCharacterEncodingFilter characterEncodingFilter = new OrderedCharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return characterEncodingFilter;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, moduleProperties.notFoundUri));
    }
}
