package org.yeolmae.challenge.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.DeleteChallengeResponse;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
import org.yeolmae.challenge.service.ChallengeService;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/register")
    public ResponseEntity<CreateChallengeResponse> challengeCreate(@RequestBody CreateChallengeRequest request) {

        CreateChallengeResponse response = challengeService.createChallenge(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
  
    @DeleteMapping("/{challenge_id}")
    public ResponseEntity<DeleteChallengeResponse> challengeDelete(@PathVariable Integer challenge_id) {

        DeleteChallengeResponse response = challengeService.deleteChallenge(challenge_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
