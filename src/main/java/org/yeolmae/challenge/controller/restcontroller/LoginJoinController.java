package org.yeolmae.challenge.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.config.auth.PrincipalDetails;
import org.yeolmae.challenge.config.auth.PrincipalDetailsService;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.MemberRole;
import org.yeolmae.challenge.repository.MemberRepository;
import org.springframework.stereotype.Controller;

@Controller
public class LoginJoinController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    // 회원 가입창
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    // 실제로 회원 가입 이뤄짐
    @PostMapping("/join")
    public String join(Member member, @RequestParam(name = "adminRole", defaultValue = "false") boolean adminRole) {

        String rawPw = member.getPw();
        String encPw = bCryptPasswordEncoder.encode(rawPw);
        member.setPw(encPw);
        member.setMemberRole(adminRole ? MemberRole.ADMIN : MemberRole.USER);

        // 회원 정보 저장
        memberRepository.save(member);

        return "redirect:/loginForm";

    }

}