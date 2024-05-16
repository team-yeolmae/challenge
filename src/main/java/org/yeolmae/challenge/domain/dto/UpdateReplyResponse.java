package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReplyResponse {

    // challenge_id 추가
    private int challengeId;

    private Integer rno;

    private String replyer;

    private String replyText;

    // registerDate 추가
    private LocalDateTime registerDateTime;

    private LocalDateTime modifyDateTime;

}