package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Member member = memberRepository.findMemberByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));

        return member;
    }

}
