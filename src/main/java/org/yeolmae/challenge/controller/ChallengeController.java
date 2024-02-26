package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@ResponseBody
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<CreateChallengeResponse> postCreate(@RequestBody CreateChallengeRequest request) {

        CreateChallengeResponse response = challengeService.createPost(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{challenge_id}")
    public ResponseEntity<ReadChallengeResponse> postRead(@PathVariable Integer challenge_id) {

        ReadChallengeResponse response = challengeService.readPostById(challenge_id);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}
