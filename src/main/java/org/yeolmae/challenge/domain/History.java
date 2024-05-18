package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @OneToOne
    private Challenge challenge;

    @Column(name = "success")
    private boolean success;

    @Builder
    public History(Member member, Challenge challenge, boolean success) {
        this.member = member;
        this.challenge = challenge;
        this.success = success;
    }

}