package com.dwshu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2     //开启swagger2
@EnableEurekaClient
@SpringBootApplication
public class EurekaServerProviderApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServerProviderApplication.class, args);
    }

}
