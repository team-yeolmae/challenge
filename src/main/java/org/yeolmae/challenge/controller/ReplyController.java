package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.ReadReplyResponse;
import org.yeolmae.challenge.service.ReplyService;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{rno}")
    public ResponseEntity<ReadReplyResponse> challengeRead(@PathVariable Integer rno) {

        ReadReplyResponse response = replyService.readChallengeById(rno);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }



}
