package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@ResponseBody
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<Page<ReadChallengeResponse>> ReadAll(@PageableDefault(
            size = 5, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReadChallengeResponse> response = challengeService.readAll(pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
