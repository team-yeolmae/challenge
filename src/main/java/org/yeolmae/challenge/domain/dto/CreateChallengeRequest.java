package org.yeolmae.challenge.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private String writer;
    @NotEmpty
    private String content;
    private LocalDate registerDate;
    private LocalDate startDate;
    private LocalDate endDate;
  
}
