package org.yeolmae.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.MemberJoinRequest;
import org.yeolmae.challenge.domain.dto.MemberJoinResponse;
import org.yeolmae.challenge.repository.MemberJoinRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberJoinService {

    private final MemberJoinRepository joinRepository;

    @Transactional
    public MemberJoinResponse joinMember(MemberJoinRequest request) {

        Member member = Member.builder()
                .email(request.getEmail())
                .pw(request.getPw())
                .nickname(request.getNickname())
                .build();

        Member savedMember = joinRepository.save(member);

        return new MemberJoinResponse(savedMember.getId(), savedMember.getEmail(), savedMember.getPw(), savedMember.getNickname());

    }
}
