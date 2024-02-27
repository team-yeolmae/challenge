package org.yeolmae.challenge.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int challenge_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(updatable = false)
    private LocalDate register_date;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate end_date;

}
