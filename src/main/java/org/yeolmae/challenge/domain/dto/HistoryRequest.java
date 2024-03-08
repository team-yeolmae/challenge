package org.yeolmae.challenge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yeolmae.challenge.domain.History;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRequest {

    private int memberId;

}