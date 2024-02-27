package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.service.ChallengeService;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/{challenge_id}")
    public ResponseEntity<ReadChallengeResponse> challengeRead(@PathVariable Integer challenge_id) {

        ReadChallengeResponse response = challengeService.readChallengeById(challenge_id);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }


}
