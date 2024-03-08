package org.yeolmae.challenge.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yeolmae.challenge.domain.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalDetails implements UserDetails {

    // Member 정보 // 컴포지션
    private Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    public String getNickname() {
        return member.getNickname();
    }

    public int getLevel() {
        return member.getLevel();
    }

    @Override // 해당 member의 권한 리턴하는 곳
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(member.getMemberRole().toString()));

        return roles;
    }

    @Override
    public String getPassword() {
        return member.getPw();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override // 계정 만료 안 됨. => true
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠기지 않음. => true
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀 번호 만료 안됨. => true
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정 활성 안됨 => true
    public boolean isEnabled() {
        return true;
    }
}