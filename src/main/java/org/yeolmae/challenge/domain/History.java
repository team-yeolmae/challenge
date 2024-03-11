package org.yeolmae.challenge.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"member", "challenge"})
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "memberId", referencedColumnName = "id", nullable = false)
//    @JsonBackReference
    private Member member;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "challengeId", referencedColumnName = "id", nullable = false)
//    @JsonBackReference
//    private Challenge challenge;

    @OneToOne
    private Challenge challenge;

    @Column(name = "success")
    private boolean success;

}