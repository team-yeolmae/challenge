package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "image_detail", length = 500)
    private String image_detail;

    @Column(name = "image_thumb", nullable = false, length = 500)
    private String image_thumb;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "challengeId", referencedColumnName = "id")
    Challenge challenge;

    public void changeChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

}