package com.qkl.background;

import com.qkl.common.web.CommonApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories("com.qkl.**.jpa")
@ComponentScan(basePackages = "com.qkl")
@EntityScan("com.qkl.**.entity")
@EnableAsync
public class Application extends CommonApplication {


    private Logger logger= LoggerFactory.getLogger(this.getClass());


    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Application.class, WebConfig.class).run(args);
    }


}
