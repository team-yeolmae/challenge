package org.yeolmae.challenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeRequest {

    private String title;
    private String writer;
    private String content;
    private LocalDate register_date;
    private LocalDate start_date;
    private LocalDate end_date;
  
}
