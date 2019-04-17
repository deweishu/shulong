package com.qkl.manage;

import com.qkl.admin.service.ConfigService;
import com.qkl.apk.es.EsActivityOperate;
import com.qkl.apk.es.EsApkOperate;
import com.qkl.common.web.CommonApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by dengjihai on 2018/2/27.
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories("com.qkl.**.jpa")
@ComponentScan(basePackages = "com.qkl")
@EntityScan("com.qkl.**.entity")
@EnableAsync
public class Application extends CommonApplication implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ConfigService configService;

    @Autowired
    EsActivityOperate esActivityOperate;

    @Autowired
    EsApkOperate esApkOperate;

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Application.class, WebConfig.class).run(args);
    }

    /**
     * 容器启动后执行+
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if(event.getApplicationContext().getParent()==null){
                //加载系统配置
                configService.findAllConfig();
            }
            esActivityOperate.init();
            esApkOperate.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
