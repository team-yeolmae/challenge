package org.yeolmae.challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.Arrays;
import java.util.List;

// @OpenAPIDefinition : swagger에서 제공하는 작성법
@OpenAPIDefinition(
        info = @Info(title = "Challenge Mission🐱‍🚀", description = "Challenge Mission API 명세", version = "v1")
)
@Configuration
public class SwaggerConfig {

    // 토큰이 필요하지 않은 API URL
    List<String> list = Arrays.asList(
//            "/api/v1/auth/login",
//            "/api/v1/members/user",
//            "/api/v1/members/admin",
//            "/api/v1/home/postList",
//            "/api/v1/home/communityList",
//            "/api/v1/home/popularCommunityList"
    );

    @Bean
    public OpenAPI oepnAPI() {
        SecurityScheme securityScheme = getSecurityScheme();
        SecurityRequirement securityRequirement = getSecurityRequirement();

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(List.of(securityRequirement));
    }

    // 보안 관련 헤더 추가를 위한 설정
    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }

    // 각 API에 대한 보안 요구 지정
    private SecurityRequirement getSecurityRequirement () {
        SecurityRequirement securityRequirement = new SecurityRequirement();

        // 보안이 필요한 API에 대한 SecurityRequirement 생성
        securityRequirement.addList("bearerAuth");

        // 보안이 필요하지 않은  API 제외처리
        for (String endpoint : list) {
            securityRequirement.remove(endpoint);
        }

        return securityRequirement;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        return multipartResolver;
    }

}