package org.yeolmae.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.MemberJoinRequest;
import org.yeolmae.challenge.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int save(MemberJoinRequest request) {
        return memberRepository.save(Member.builder()
                .email(request.getEmail())
                .pw(bCryptPasswordEncoder.encode(request.getPw()))
                .nickname(request.getNickname())
                .build()).getId();
    }

}
