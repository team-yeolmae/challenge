package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yeolmae.challenge.domain.Challenge;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {

    private int id;
//    private int memberId;
    private Challenge challenge;
    private boolean success;

}
