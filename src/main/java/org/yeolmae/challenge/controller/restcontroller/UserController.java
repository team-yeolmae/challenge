package org.yeolmae.challenge.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.MemberUpdateRequest;
import org.yeolmae.challenge.repository.MemberRepository;

@RestController
public class UserController { // 회원 정보 수정

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PutMapping("/user")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest updateRequest) {
        Member member = memberRepository.findMemberByEmail(updateRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (updateRequest.getPw() != null && !updateRequest.getPw().isEmpty()){
            member.setPw(bCryptPasswordEncoder.encode(updateRequest.getPw()));
        }

        member.setNickname(updateRequest.getNickname());

        memberRepository.save(member);

        return ResponseEntity.ok().build();

    }
}
