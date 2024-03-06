package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yeolmae.challenge.domain.ReplyImage;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReplyResponse {

    // challenge_id 추가
    private int challengeId;

    private Integer rno;

    private String replyer;

    private String replyText;

    // registerDate 추가
    private LocalDate registerDate;

    private ReplyImage[] replyImages;

}