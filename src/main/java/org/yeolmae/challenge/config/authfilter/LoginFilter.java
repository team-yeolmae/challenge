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
 * Authentication(����)
 * => ������� �ſ��� Ȯ���ϴ� ����, ���̵�� ��� Ȯ��
 * Authorization(�ΰ�)
 * => ������ ����ڰ� Ư�� �ڿ��� ������ �� �ִ� "������ �ο�"�ϴ� ����, USER�� ADMIN
 *
 * �α��� ��û�� ó���ϴ� ���� ����� ���Ժ��� AbstractAuthenticationProcessingFilter
 */

@Log4j2
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    /** access token�� �̿��Ͽ� ��Ʈ�ѷ� ȣ�� �� ������ ������ üũ�ϴ� ���
     *
     * AbstractAuthenticationProcessingFilter : �α��� ó�� ���
     * AuthenticationManager ���� �ʼ� -> SecurityConfig����! */

    public LoginFilter(final String defaultFilterProcessesUrl,
                       final AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    /**
     * UsernamePasswordAuthenticationFilter
     * => �α��� ��û�� ���� ������ ������ ������ �ʿ��� ��ü�� UsernamePasswordAuthenticationToken ���� ��ȯ
     * => AuthenticationMananger ��, ���� �Ŵ������� ������ ����
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        /** ���� ��û ó�� �޼ҵ�
         *  ��û�� �м��ϰ� ���� ��ū�� �����Ͽ� ���� �Ŵ����� ����
         */
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: "+request.getMethod());
        }

        /**
         * Rest ������� JSON �����͸� �����ϸ� inputStream �� Stream ���·� ���� ����
         * �����͸� �״�� ObjectMapper�� ���ؼ� LoginRequestDto ��ü�� ���ε��ؼ� ����
         * UsernamePasswordAuthenticationToken���� ���� ���� ��û
         * LoginRequest �� Property �� ���缭 JSON �����͸� ��û
         */
        LoginRequest loginRequest = new ObjectMapper().
                readValue(request.getInputStream(), LoginRequest.class);

//        // Ŭ���̾�Ʈ���� POST ��û �� �Ľ̵� JSON ���ڿ� ó�� �޼ҵ�
//        Map<String, String> jsonData = parseRequestJSON(request);
//        log.info(jsonData);

        /** UsernamePasswordAuthenticationFilter
         * ������ �����͸� �޾Ƽ� ���� ��ü�� ����� ����
         * �� Filter�� form ����� �����͸� �ޱ� ������ ����
         */
        // ���� ��ū ����
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw);

        return getAuthenticationManager().authenticate(token);
    }

    /**
     * record
     * => Java 14���� ���Ե� ���ο� Ŭ���� Ÿ�� && ������ ��ü�� �����ϰ� ������ �� �ֵ��� ����
     * => �� �� ������ �Ŀ��� �ʵ� ���� ���� X
     * => ������, ������(getter), equals(), hashCode(), toString() �޼��尡 �ڵ����� ����
     */
    public record LoginRequest(String email, String pw){}

}
