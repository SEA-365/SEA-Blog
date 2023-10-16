package com.sea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.PathDecorator;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * @author: sea
 * @date: 2023/10/13 19:45
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 创建Docket实例
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sea.controller")) // 指定要生成文档的接口所在的包
                .paths(PathSelectors.any()) // 指定要生成文档的接口的路径匹配规则
                .build();
    }

    /**
     * 设置文档的基本信息
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SEA-Blog后端接口文档") // 设置文档标题
                .description("SEA-Blog后端") // 设置文档描述
                .contact(new Contact("SEA", "https://gitee.com/sea-365", "Email")) // 设置文档联系人信息
                .version("1.0") // 设置文档版本号
                .build();
    }
}