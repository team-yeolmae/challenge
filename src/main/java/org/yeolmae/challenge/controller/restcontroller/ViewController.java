package org.yeolmae.challenge.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yeolmae.challenge.repository.MemberRepository;

@Controller // View를 리턴
public class ViewController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @Secured("ROLE_ADMIN") //특정경로 권한
    @GetMapping("/challenge/register")
    public @ResponseBody String registerChallenge() {
        return "챌린지 등록 페이지";
    }

    @GetMapping("/user/mypage")
    public String getMypage() {
        return "mypage";
    }

    @GetMapping("/user/mypage/profile")
    public String getMypageProfile() {
        return "profile";
    }

    @PostMapping("/user/mypage/updateProfile")
    public String updateMypageProfile() {
        return "updateProfile";
    }

}