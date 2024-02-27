package org.yeolmae.challenge.config;

import lombok.Builder;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.yeolmae.challenge.service.security.MemberDetailService;

@Configuration
// @EnableWebSecurity : SpringSecurity FilterChain이 자동으로 포함
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    //특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

//        //스프링 시큐리티 버전으로 인해 람다식으로 작성.
//        httpSecurity.formLogin((formLogin) -> formLogin.loginPage("/challenge/join"));
        // 권한에 따른 허용하는 url
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/login","/join").permitAll()
                        .anyRequest().authenticated());
        // login 설정
        http.formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/main"));
        // logout 설정
        http.logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true));
        //토큰 비활성화
        http.csrf((csrf) -> csrf.disable());

        return http.build();
    }

    // 비밀번호 암호화 - BCryptPasswordEncoder() 해시 함수 이용하여 암호화 처리
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // css 나 js 파일 등의 정적 파일은 시큐리티 적용을 받을 필요 없이 무시하도록 함.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
