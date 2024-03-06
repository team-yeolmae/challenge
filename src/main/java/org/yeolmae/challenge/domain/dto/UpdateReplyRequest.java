package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yeolmae.challenge.domain.ReplyImage;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReplyRequest {

    private String replyText;

    private ReplyImage[] replyImages;

}
