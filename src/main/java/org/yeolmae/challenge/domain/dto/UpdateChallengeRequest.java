package org.yeolmae.challenge.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UpdateChallengeRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer;

    private LocalDate registerDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
