package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.MemberJoinRequest;
import org.yeolmae.challenge.domain.dto.MemberJoinResponse;
import org.yeolmae.challenge.service.MemberJoinService;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberJoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<MemberJoinResponse> memberJoin(@RequestBody MemberJoinRequest request) {

        MemberJoinResponse response = joinService.joinMember(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }





}
