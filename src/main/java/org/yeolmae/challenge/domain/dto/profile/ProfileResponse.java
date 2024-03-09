package org.yeolmae.challenge.domain.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private String email;
    private String pw;
    private String nickname;

}
