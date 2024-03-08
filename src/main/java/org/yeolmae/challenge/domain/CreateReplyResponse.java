package org.yeolmae.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReplyResponse {

    // challenge_id 추가
    private int challengeId;

    private String replyer;

    private String replyText;

    // registerDate 추가
    private LocalDate registerDate = LocalDate.now();

    private List<MultipartFile> images;

}