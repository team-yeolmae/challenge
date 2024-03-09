package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReplyRequest {

    private String replyText;
//    private List<MultipartFile> images;

}