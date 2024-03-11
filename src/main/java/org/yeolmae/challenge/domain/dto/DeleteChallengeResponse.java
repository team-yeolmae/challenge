package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteChallengeResponse {

    private int id;
    private String title;
    private String writer;
    private String content;
    private LocalDate registerDate;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<String> fileNames;


}
