package org.yeolmae.challenge.domain.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yeolmae.challenge.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequest {

    private String email;
    private String pw;
    private String nickname;

}
