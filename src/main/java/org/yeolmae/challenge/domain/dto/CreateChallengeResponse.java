package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeResponse {

    private int id;
    private String title;
    private String writer;
    private String content;
    private LocalDate registerDate;
    private LocalDate startDate;
    private LocalDate endDate;
  
}
