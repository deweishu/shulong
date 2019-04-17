package com.qkl.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Swagger2 {


    @Bean
    public Docket createRestApi() {
        //  RequestHandlerSelectors.any(); 是扫描全部，但是目前不适用于当前项目中，他会将spring框架内自带所有的都扫描到
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("token")
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qkl.api"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("邓集海","http://localhost:8989/","dengjhjava@foxmail.com");
        return new ApiInfoBuilder()
                .title("Chain Store 接口文档")
                .description("Chain Store 接口文档")
                .termsOfServiceUrl("http://www.baidu.com/")
                .contact(contact)
                .version("1.0")
                .build();
    }

}
