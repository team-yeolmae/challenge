package org.yeolmae.challenge.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.DeleteChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@ResponseBody
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @DeleteMapping("/{challenge_id}")
    public ResponseEntity<DeleteChallengeResponse> Delete(@PathVariable Integer challenge_id) {

        DeleteChallengeResponse response = challengeService.delete(challenge_id);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
