package org.yeolmae.challenge.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(updatable = false, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int level;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public void changePassword(String pw) {
        this.pw = pw;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

//    @JsonManagedReference
//    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
//    private Set<History> histories = new HashSet<>();

}