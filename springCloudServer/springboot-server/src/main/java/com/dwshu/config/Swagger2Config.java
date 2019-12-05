package com.dwshu.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author dwshu
 * @create 2019/11/28
 */
@Configuration
public class Swagger2Config {

    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dwshu.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //文档标题
                .title("elasticsearch创建测试索引库集成swagger2")
                //文档描述
                .description("springboot-elasticSearch 网关接口，http://localhost:8083/")

                .termsOfServiceUrl("http://localhost:8083/")
                //联系方式
                .contact("deweishu@gmail.com")
                .version("1.0.0")
                .build();
    }
}
