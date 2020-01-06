package com.dwshu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching  //用于RedisConfig配置类
@EnableSwagger2
@SpringBootApplication()
public class SpringbootServerApplication {

    public static void main(String[] args) {

        /*
         * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错，解决netty冲突后初始化client时还会抛出异常
         * Error creating bean with name 'elasticsearchClient' defined in class path resource [org/springframework/boot/autoconfigure/data/elasticsearch/ ElasticsearchAutoConfiguration.class]: Bean instantiation via factory method failed;
         * nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.elasticsearch.client.transport.TransportClient]: Factory method 'elasticsearchClient' threw exception;
         * nested exception is java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
         */
        //添加这个条代码解决上面异常
        System.setProperty("es.set.netty.runtime.available.processors","false");

        SpringApplication.run(SpringbootServerApplication.class, args);
    }

}
