package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.profile.ProfileResponse;
import org.yeolmae.challenge.domain.dto.profile.ProfileUpdateRequest;
import org.yeolmae.challenge.domain.dto.profile.ProfileUpdateResponse;
import org.yeolmae.challenge.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
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

    @Transactional
    public ProfileUpdateResponse profileUpdate(ProfileUpdateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Member foundMember = memberRepository.findMemberByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));

        foundMember.changePassword(request.getPw());
        foundMember.changeNickname(request.getNickname());

        log.info(request.getPw(),request.getNickname());

        return new ProfileUpdateResponse(foundMember.getEmail(), foundMember.getPw(), foundMember.getNickname());
    }

//    @Transactional
//    public MemberUpdateResponse updateMember(String email, MemberUpdateRequest memberUpdateRequest) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        String username = userDetails.getUsername();
//
//        Member member = memberRepository.findMemberByEmail(username)
//                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));
//
//        member.update(memberUpdateRequest.getPw(),memberUpdateRequest.getNickname());
//
//    }

}
