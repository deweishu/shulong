package com.tensquare.crawler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import util.IdWorker;

@EnableScheduling
@SpringBootApplication
public class CrawlerApplication {

    @Value("${redis.host}")
    private String redis_host;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return  new IdWorker(1,1);
    }

    //注册Redis队列
    @Bean
    public RedisScheduler redisScheduler(){
        return  new RedisScheduler(redis_host);
    }

}
