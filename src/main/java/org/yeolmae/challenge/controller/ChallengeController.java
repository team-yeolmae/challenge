package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.UpdateChallengeRequest;
import org.yeolmae.challenge.domain.dto.UpdateChallengeResponse;
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@ResponseBody
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PutMapping("/{challenge_id}")
    public ResponseEntity<UpdateChallengeResponse> postUpdate(@PathVariable Integer challenge_id,
                                                              @RequestBody UpdateChallengeRequest request){

        UpdateChallengeResponse response = challengeService.updatePost(challenge_id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
