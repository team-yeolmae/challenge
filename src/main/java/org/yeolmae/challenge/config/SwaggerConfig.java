package org.yeolmae.challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

// @OpenAPIDefinition : swagger에서 제공하는 작성법
@OpenAPIDefinition(
        info = @Info(title = "Challenge Mission🐱‍🚀", description = "Challenge Mission API 명세", version = "v1")
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi firstOpenApi() {

        String[] path = {
                "org.yeolmae.challenge.controller"
        };

        return GroupedOpenApi.builder()
                .group("챌린지 어플 API")
                .packagesToScan(path)
                .build();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        return multipartResolver;
    }

}