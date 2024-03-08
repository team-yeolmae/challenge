package org.yeolmae.challenge.domain;

import jakarta.persistence.*;

@Entity
public class ChallengeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "challengeId", referencedColumnName = "id")
    Challenge challenge;

    @Column(name = "image_detail", length = 500)
    private String image_detail;

    @Column(name = "image_thumb", nullable = false, length = 500)
    private String image_thumb;


}
