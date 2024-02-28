package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.dto.CreateReplyRequest;
import org.yeolmae.challenge.domain.dto.CreateReplyResponse;
import org.yeolmae.challenge.service.ReplyService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity<CreateReplyResponse> replyCreate(@RequestBody CreateReplyRequest request) {

        CreateReplyResponse response = replyService.createReply(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
