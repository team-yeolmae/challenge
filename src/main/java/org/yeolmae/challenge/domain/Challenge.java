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

    public void update(String title, String writer, String content, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.startDate = start_date;
        this.endDate = end_date;
    }

    @OneToOne
    private History history;

}
