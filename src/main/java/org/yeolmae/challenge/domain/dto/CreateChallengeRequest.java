package org.yeolmae.challenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;
import org.yeolmae.challenge.domain.Challenge;

import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeRequest {

    private String title;
    private String writer;
    private String content;
    private LocalDate registerDate;
    private LocalDate startDate;
    private LocalDate endDate;

}
