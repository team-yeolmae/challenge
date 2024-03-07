package org.yeolmae.challenge.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.repository.MemberRepository;

// 시큐리티 설정에서 loginProcessionUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {


    @Autowired
    private MemberRepository memberRepository;

    // 로그인 구현 // email로 사용자 정보 조회
    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member memberEntity = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("로그인 정보를 확인하세요."));

        UserDetails userDetails = User.builder()
                .username(memberEntity.getEmail())
                .password(memberEntity.getPw())
                .roles(memberEntity.getMemberRole().toString())
                .build();

        return userDetails;
    }

}
