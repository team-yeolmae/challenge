package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(updatable = false, unique = true)
    private String email;

    private String pw;

    private String nickname;

    public Member(String email, String pw, String nickname, String auth) {
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
    }

    //권한 부여 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자 id 반환 (중복x)
    @Override
    public String getUsername() {
        return email;
    }

    //사용자 패스워드 반환
    @Override
    public String getPassword() {
        return pw;
    }

    //계정 만료 여부 반환 - 만료 하지 않음.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정 잠금 여부 반환 - 잠금 하지 않음.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //패스워드 만료 여부 반환 - 만료 하지 않음.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정 사용 가능 여부 반환 - 사용 가능.
    @Override
    public boolean isEnabled() {
        return true;
    }

    //    @ElementCollection(fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Level> level = new HashSet<>();
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String pw) {
        this.pw = pw;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }


}
