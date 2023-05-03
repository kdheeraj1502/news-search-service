package com.news.aggregator.search.doc;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan
public class SwaggerConfig {
    @Bean
    public Docket docketInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.news.aggregator.search.controllers"))
                .paths(regex("/v1/news.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "News Aggregator Service",
                "News Search Service provides updated news based on user search, from NYTimes US and Guardian UK. It covers the international news",
                "1.0",
                "Terms of Service",
                new Contact("Dheeraj Kumar", "https://github.com/kdheeraj1502",
                        "kdheeraj1512@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html"
        );

        return apiInfo;
    }

}