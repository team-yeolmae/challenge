package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "registerDate", updatable = false, nullable = false)
    private LocalDate registerDate;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "challenge", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true) // 어떤 Entity의 속성으로 매핑하는지 // ChallengeImage의 challenge 변수
    @Builder.Default
    private Set<ChallengeImage> imageSet = new HashSet<>();

    public void addChallengeImage(String uuid, String fileName) {

        ChallengeImage challengeImage = ChallengeImage.builder()
                .image_detail(uuid)  // 파일 uuid 저장
                .image_thumb(fileName)   // 파일 이름 저장
                .build();

        this.imageSet.add(challengeImage);
        challengeImage.setChallenge(this);

    }

    public void clearChallengeImage() {

        imageSet.forEach(challengeImage -> challengeImage.changeChallenge(null));
        this.imageSet.clear();

    }

    public void update(String title, String writer, String content, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @OneToOne
    private History history;

}