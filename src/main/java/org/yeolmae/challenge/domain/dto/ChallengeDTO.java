package org.yeolmae.challenge.domain.dto;

import java.time.LocalDate;
import java.util.List;

public class ChallengeDTO {

    private int id;
    private String title;
    private String writer;
    private String content;
    private LocalDate registerDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> fileNames;

}