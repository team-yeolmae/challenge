package org.yeolmae.challenge.domain.dto.login;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String pw;
    private String nickname;

}