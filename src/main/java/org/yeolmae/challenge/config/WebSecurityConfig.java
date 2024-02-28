package org.yeolmae.challenge.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebSecurity : SpringSecurity FilterChain이 자동으로 포함
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    // css 나 js 파일 등의 정적 파일은 시큐리티 적용을 받을 필요 없이 무시하도록 함.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 권한에 따른 허용하는 url
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated());
        // login 설정
        http.formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/home"));
        // logout 설정
        http.logout((logout) -> logout
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true));
        //csrf 비활성화
        http.csrf((csrf) -> csrf.disable());

        return http.build();
    }

    // UserDetailsService 및 PasswordEncoder를 사용하여 사용자 아이디와 암호를 인증하는 AuthenticationProvider 구현
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // 비밀번호 암호화 - BCryptPasswordEncoder() 해시 함수 이용하여 암호화 처리
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
