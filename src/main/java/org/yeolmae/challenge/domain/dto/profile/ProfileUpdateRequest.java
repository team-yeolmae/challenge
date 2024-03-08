package org.yeolmae.challenge.domain.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {

    private String pw;
    private String nickname;

}
