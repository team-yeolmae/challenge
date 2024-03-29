package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReplyRequest {

    // challenge_id 추가
    private int challengeId;

    private String replyer;

    private String replyText;

    // registerDate 추가
    private LocalDate registerDate = LocalDate.now();

}