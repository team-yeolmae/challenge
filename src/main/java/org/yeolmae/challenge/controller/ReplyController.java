package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.UpdateReplyRequest;
import org.yeolmae.challenge.domain.dto.UpdateReplyResponse;
import org.yeolmae.challenge.service.ReplyService;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PutMapping("/{rno}")
    public ResponseEntity<UpdateReplyResponse> updateReply(@PathVariable Integer rno,
                                                               @RequestBody UpdateReplyRequest request){

        UpdateReplyResponse response = replyService.replyUpdate(rno, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
