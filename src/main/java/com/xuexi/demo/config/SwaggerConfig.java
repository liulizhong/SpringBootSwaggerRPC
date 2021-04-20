package com.xuexi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author
 * @version TODO
 * @class swagger的web访问地址是http://localhost:8080/swagger-ui.html
 * @CalssName SwaggerConfig
 * @create 2020-07-24 15:47
 * @Des TODO
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.xuexi.demo"})  // 萨满秒API Controller包
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
/*        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();*/
    }

    private ApiInfo apiInfo() {
        // 显示作者和参考文档位置，以及发邮件链接
        Contact contact = new Contact("rh",
//                "http://10.238.251.3:50075",
                "http://localhost:50075",
                "liulizhong@rhtect.com"
        );
        return new ApiInfoBuilder()
                .title("大数据采集后端任务接口")
                .description("springboot整合swaggerUI，提供自动化数据的管理功能！！！！")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}