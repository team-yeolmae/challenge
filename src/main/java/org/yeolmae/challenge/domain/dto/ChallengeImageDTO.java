package org.yeolmae.challenge.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChallengeImageDTO {

    @NotEmpty
    private String image_detail;
    @NotEmpty
    private String image_thumb;

}
