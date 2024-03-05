package org.yeolmae.challenge.domain.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReplyResponse {

    private Integer rno;

    private String replyer;

    private String replyText;

}
