package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "challenge")
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

    public void changeChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

}