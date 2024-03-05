package org.yeolmae.challenge.domain.dto.upload;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadFileRequest {

    private List<MultipartFile> files;

}
