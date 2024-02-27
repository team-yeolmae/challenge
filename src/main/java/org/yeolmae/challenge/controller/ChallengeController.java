package org.yeolmae.challenge.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.DeleteChallengeResponse;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.domain.dto.UpdateChallengeRequest;
import org.yeolmae.challenge.domain.dto.UpdateChallengeResponse;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
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

    @PostMapping("/register")
    public ResponseEntity<CreateChallengeResponse> challengeCreate(@RequestBody CreateChallengeRequest request) {

        CreateChallengeResponse response = challengeService.createChallenge(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{challenge_id}")
    public ResponseEntity<UpdateChallengeResponse> challengeUpdate(@PathVariable Integer challenge_id,
                                                                   @RequestBody UpdateChallengeRequest request){

        UpdateChallengeResponse response = challengeService.updateChallenge(challenge_id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ReadChallengeResponse>> challengeReadAll(@PageableDefault(
            size = 5, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReadChallengeResponse> response = challengeService.readAllChallenge(pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{challenge_id}")
    public ResponseEntity<DeleteChallengeResponse> challengeDelete(@PathVariable Integer challenge_id) {

        DeleteChallengeResponse response = challengeService.deleteChallenge(challenge_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
