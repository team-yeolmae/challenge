package org.yeolmae.challenge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/*
 * series 값과 token 값을 따로 만들어서 저장하기 때문에 유저네임과 만료시간이 노출되지 않는다.
 * 다른 브라우저에 로그인 할 때마다 series 값 변경한다/.
 * 최근 로그인 한 계정의 series 값을 기준으로 token 값을 검사하기 때문에 보안상 좀 더 안전하다.
 * */
@Entity
@Table(name = "persistent_logins")
@Getter
@Setter
public class PersistentLogin {

    @Id
    private String series;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String token;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime last_used;

}