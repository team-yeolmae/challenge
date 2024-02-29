package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    //    @ElementCollection(fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Level> level = new HashSet<>();

    public void changePassword(String pw) {
        this.pw = pw;
    }
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
