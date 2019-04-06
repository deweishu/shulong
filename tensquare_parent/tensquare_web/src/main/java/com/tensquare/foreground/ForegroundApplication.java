package com.tensquare.foreground;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ForegroundApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForegroundApplication.class,args);
    }



}
