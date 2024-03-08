package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReplyRequest {

    // challenge_id 추가
    private int challengeId;

    private String replyer;

    private String replyText;

    // registerDate 추가
    private LocalDate registerDate = LocalDate.now();

    private List<MultipartFile> images;

}