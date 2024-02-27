package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.MemberJoinRequest;
import org.yeolmae.challenge.service.MemberService;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberService memberService;

    @PostMapping("/user")
    public String signup(MemberJoinRequest request) {

        memberService.save(request);

        return "redirect:/login";
    }


}
