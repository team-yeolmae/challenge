package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.ChallengeImage;
import org.yeolmae.challenge.domain.dto.upload.UploadFileResponse;

import java.time.LocalDate;
import java.util.List;

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
