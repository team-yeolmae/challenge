package org.yeolmae.challenge.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.yeolmae.challenge.config.auth.PrincipalDetailsService;
import org.yeolmae.challenge.config.authfilter.LoginFilter;
import org.yeolmae.challenge.repository.MemberRepository;

import javax.sql.DataSource;

@Configuration
// @EnableWebSecurity : SpringSecurity FilterChain이 자동으로 포함
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)// secured, PreAuthorize/postAuthorize 어노테이션 활성화 //특정 경로에 접근할 수 있는 권한
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    //특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 커스텀 인증 설정을 추가
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = sharedObject.build(); // 사용자 인증 처리
        http.authenticationManager(authenticationManager);

        // 권한에 따른 허용하는 url
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/user/**").authenticated()//인증(로그인) 해야함.
                .requestMatchers("/admin/**").hasRole("ADMIN")//권한 있어야 함.
                .anyRequest().permitAll());//나머지 페이지들은 모두 권한 허용

        // 헤더 설정
        http.headers(httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // 웹 페이지가 다른 사이트에서 프레임되는 것을 방지
                        .contentSecurityPolicy(contentSecurityPolicyConfig -> // XSS(Cross-Site Scripting) 및 데이터 삽입 공격을 방지
                                contentSecurityPolicyConfig.policyDirectives(
                                        // 아래의 것들을 현재의 출처에서만 로드가 가능할 수 있도록 함.
                                        "script-src 'self'; "
                                        + "img-src 'self'; "
                                        + "font-src 'self'; "
                                        + "default-src 'self'; "
                                        + "frame-src 'self'"))
        );

        // 사용자 정의 인증 필터 추가
        http.addFilterAt(
                this.abstractAuthenticationProcessingFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class
        );

        // login 설정
        http.formLogin((formLogin) -> formLogin
                .loginPage("/loginForm")
                .usernameParameter("email")
                .loginProcessingUrl("/login")// /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행 // 로그인을 Spring Security가 대신 위임해서 처리해주는 기능(사용자 아이디(이메일), 비번 인증하는 것 대신 해줌.)
                .defaultSuccessUrl("/main"));// "/" -> main 혹은 home 화면

        // 자동로그인 설정
        http.rememberMe((rememberMe) -> rememberMe
                .key("rememberMeKey")//인증받은 사용자 정보로 토큰 생성에 필요한 값
                .rememberMeParameter("remember-me")//html에서의 name 값
                .tokenValiditySeconds(24*60*60)//remember-me 토큰 유효시간 : 1일
                .rememberMeServices(rememberMeServices(persistentTokenRepository()))
                .userDetailsService(new PrincipalDetailsService()));

        // logout 설정
        http.logout((logout) -> logout
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/loginForm"));

        //csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        http.securityContext((securityContext) -> securityContext
                .securityContextRepository(new DelegatingSecurityContextRepository(
                        new RequestAttributeSecurityContextRepository(),
                        new HttpSessionSecurityContextRepository()
                )));

        return http.build();
    }

    // 사용자 정의 인증 필터 생성 방법
    public AbstractAuthenticationProcessingFilter abstractAuthenticationProcessingFilter(final AuthenticationManager authenticationManager) {
        return new LoginFilter(
                "/api/login", // 특정 경로에 대한 인증 처리
                authenticationManager
        );
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);

        tokenRepository.setCreateTableOnStartup(false);

        return tokenRepository;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices(PersistentTokenRepository tokenRepository){
        PersistentTokenBasedRememberMeServices rememberMeServices
                = new PersistentTokenBasedRememberMeServices("rememberMeKey", new PrincipalDetailsService(), tokenRepository);
        rememberMeServices.setParameter("remember-me");
        rememberMeServices.setAlwaysRemember(false);

        return rememberMeServices;
    }

    // 비밀번호 암호화 - BCryptPasswordEncoder() 해시 함수 이용하여 암호화 처리
    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // UserDetailsService 및 PasswordEncoder를 사용하여 사용자 아이디와 암호를 인증하는 AuthenticationProvider 구현
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // css 나 js 파일 등의 정적 파일은 시큐리티 적용을 받을 필요 없이 무시하도록 함.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}