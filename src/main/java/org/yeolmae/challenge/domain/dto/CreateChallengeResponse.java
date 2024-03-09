package org.yeolmae.challenge.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.yeolmae.challenge.domain.ChallengeImage;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CreateChallengeResponse {

    private int id;
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

    private Set<ChallengeImage> fileNames;

}
