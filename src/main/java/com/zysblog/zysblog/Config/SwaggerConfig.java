package com.zysblog.zysblog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //配置了swagger的Docket的bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("zyblogs", "http://localhost:8008", "1417243391@qq.com");
        return new ApiInfo(
                "swagger",
                "文档",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }

}
