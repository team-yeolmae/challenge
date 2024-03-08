package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.History;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MypageResponse {

    private String nickname;
    private int level;
    private List<HistoryResponse> historyResponseList;

}