package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "memberId", referencedColumnName = "id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "Id")
    private Challenge challenge;

    @Column(name = "success")
    private boolean success;

}
