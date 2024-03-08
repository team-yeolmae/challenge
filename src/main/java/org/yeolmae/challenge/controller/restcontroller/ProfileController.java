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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberService memberService;

    @GetMapping("/mypage/profile")
    public ResponseEntity<ProfileResponse> getProfile(){

        Member member = memberService.getMember();

        ProfileResponse response = new ProfileResponse(member.getEmail(), member.getPw(), member.getNickname());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/mypage/profile")
    public ResponseEntity<ProfileUpdateResponse> updateProfile(@RequestBody ProfileUpdateRequest request) {

        Member member = memberService.getMember();

        ProfileUpdateResponse response = memberService.profileUpdate(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/user")
//    public String user(HttpSession session, Model model) {
//
//        String myEmail = (String) session.getAttribute("loginEmail");
//        MemberUpdateRequest memberUpdateRequest = memberService.updateForm(myEmail);
//        model.addAttribute("updateMember", memberUpdateRequest);
//
//        return "user";
//    }

//    @PutMapping("/user")
//    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest updateRequest) {
//        Member member = memberRepository.findMemberByEmail(updateRequest.getEmail())
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//
//        if (updateRequest.getPw() != null && !updateRequest.getPw().isEmpty()){
//            member.setPw(bCryptPasswordEncoder.encode(updateRequest.getPw()));
//        }
//
//        member.setNickname(updateRequest.getNickname());
//
//        memberRepository.save(member);
//
//        return ResponseEntity.ok().build();
//
//    }

}
