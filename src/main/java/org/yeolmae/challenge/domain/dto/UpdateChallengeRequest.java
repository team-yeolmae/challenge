package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChallengeRequest {

    private String title;

    private String writer;

    private String content;

    private LocalDate register_date;

    private LocalDate start_date;

    private LocalDate end_date;
}
