package org.yeolmae.challenge.controller.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.profile.ProfileResponse;
import org.yeolmae.challenge.domain.dto.profile.ProfileUpdateRequest;
import org.yeolmae.challenge.domain.dto.profile.ProfileUpdateResponse;
import org.yeolmae.challenge.repository.MemberRepository;
import org.yeolmae.challenge.service.MemberService;

@RestController
@RequestMapping("/user/api")
@RequiredArgsConstructor
public class ProfileController { // 회원 정보 수정

    private final MemberService memberService;

    @GetMapping("/mypage/profile")
    public ResponseEntity<ProfileResponse> getProfile(){

        Member member = memberService.getMember();

        ProfileResponse response = new ProfileResponse(member.getEmail(), member.getPw(), member.getNickname());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/mypage/profile")
    public ResponseEntity<ProfileUpdateResponse> updateProfile(@RequestBody ProfileUpdateRequest request) {

        ProfileUpdateResponse response = memberService.profileUpdate(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
