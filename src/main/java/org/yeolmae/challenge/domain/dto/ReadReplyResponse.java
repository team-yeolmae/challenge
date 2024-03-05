package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadReplyResponse {

    private Integer rno;

    private String replyer;

    private String replyText;

}
