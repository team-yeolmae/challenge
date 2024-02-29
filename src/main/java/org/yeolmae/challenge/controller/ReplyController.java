package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<ReadReplyResponse>> replyReadAll(@PageableDefault(
            size = 5, sort = "rno", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReadReplyResponse> response = replyService.readAllReply(pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
