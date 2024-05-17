package org.yeolmae.challenge.config.authfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

/**
 * Authentication vs Authorization
 * Authentication(인증)
 * => 사용자의 신원을 확인하는 과정, 아이디와 비번 확인
 * Authorization(인가)
 * => 인증된 사용자가 특정 자원에 접근할 수 있는 "권한을 부여"하는 과정, USER와 ADMIN
 *
 * 로그인 요청을 처리하는 가장 가까운 진입부인 AbstractAuthenticationProcessingFilter
 */

@Log4j2
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    /** access token을 이용하여 컨트롤러 호출 시 인증과 권한을 체크하는 기능
     *
     * AbstractAuthenticationProcessingFilter : 로그인 처리 담당
     * AuthenticationManager 설정 필수 -> SecurityConfig에서! */

    public LoginFilter(final String defaultFilterProcessesUrl,
                       final AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    /**
     * UsernamePasswordAuthenticationFilter
     * => 로그인 요청에 대한 정보를 가지고 인증에 필요한 객체인 UsernamePasswordAuthenticationToken 으로 변환
     * => AuthenticationMananger 즉, 인증 매니저에게 인증을 위임
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        /** 인증 요청 처리 메소드
         *  요청을 분석하고 인증 토큰을 생성하여 인증 매니저에 전달
         */
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: "+request.getMethod());
        }

        /**
         * Rest 방식으로 JSON 데이터를 전달하면 inputStream 에 Stream 형태로 값이 저장
         * 데이터를 그대로 ObjectMapper를 통해서 LoginRequestDto 객체로 바인딩해서 저장
         * UsernamePasswordAuthenticationToken으로 만들어서 인증 요청
         * LoginRequest 의 Property 에 맞춰서 JSON 데이터를 요청
         */
        LoginRequest loginRequest = new ObjectMapper().
                readValue(request.getInputStream(), LoginRequest.class);

//        // 클라이언트에서 POST 요청 시 파싱된 JSON 문자열 처리 메소드
//        Map<String, String> jsonData = parseRequestJSON(request);
//        log.info(jsonData);

        /** UsernamePasswordAuthenticationFilter
         * 실제로 데이터를 받아서 인증 객체를 만드는 역할
         * 이 Filter는 form 방식의 데이터를 받기 때문에 변경
         */
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw);

        return getAuthenticationManager().authenticate(token);
    }

    /**
     * record
     * => Java 14에서 도입된 새로운 클래스 타입 && 데이터 객체를 간편하게 정의할 수 있도록 설계
     * => 한 번 생성된 후에는 필드 값을 변경 X
     * => 생성자, 접근자(getter), equals(), hashCode(), toString() 메서드가 자동으로 생성
     */
    public record LoginRequest(String email, String pw){}

}
