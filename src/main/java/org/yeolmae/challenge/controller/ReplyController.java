package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.DeleteReplyResponse;
import org.yeolmae.challenge.service.ReplyService;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @DeleteMapping("/{rno}")
    public ResponseEntity<DeleteReplyResponse> replyDelete(@PathVariable Integer rno) {

        DeleteReplyResponse response = replyService.deleteReply(rno);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
