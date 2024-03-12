package org.yeolmae.challenge.controller.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.domain.dto.CreateReplyResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.service.ChallengeService;
import org.yeolmae.challenge.service.MemberService;
import org.yeolmae.challenge.service.ReplyService;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/api/user/reply")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;
    private final MemberService memberService;


    @PostMapping("/register")
    @Operation(summary = "댓글을 등록하는 메소드", description = "text를 입력하세요.")
    public ResponseEntity<CreateReplyResponse> createReply(
            @RequestParam("challengeId") int challengeId,
            @RequestBody CreateReplyRequest request) {

        CreateReplyResponse response = replyService.createReply(challengeId, request);

        log.info(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 목록 10개씩 paging 처리
//    @GetMapping("/list/{challengeId}")
//    @Operation(summary = "댓글 목록을 조회하는 메소드", description = "댓글 목록을 조회할 challengeId와 page를 입력하세요.")
//    public ResponseEntity<Page<ReadReplyResponse>> readAllReply(
//            @PathVariable("challengeId") int challengeId,
//            @PageableDefault(size = 10, sort = "rno", direction = Sort.Direction.DESC) Pageable pageable) {
//
//        log.info("🌹 요청===================");
//
//        Page<ReadReplyResponse> response = replyService.readAllReplies(challengeId, pageable);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/list/{challengeId}")
    @Operation(summary = "댓글 목록을 조회하는 메소드", description = "댓글 목록을 조회할 challengeId와 page를 입력하세요.")
    public ResponseEntity<Page<ReadReplyResponse>> readAllReply(
            @PathVariable("challengeId") int challengeId,
            @PageableDefault(size = 10, sort = "rno", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("🌹 요청===================");

        Page<ReadReplyResponse> response = replyService.readAllReplies(challengeId, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/read/{rno}")
    @Operation(summary = "특정 댓글을 조회하는 메소드", description = "조회할 댓글의 rno를 입력하세요.")
    public ResponseEntity<ReadReplyResponse> readReply(@PathVariable("rno") Integer rno) {

        ReadReplyResponse response = replyService.readReplyById(rno);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/modify/{rno}")
    @Operation(summary = "특정 댓글을 수정하는 메소드", description = "수정할 댓글의 rno와 수정 text를 입력하세요.")
    public ResponseEntity<UpdateReplyResponse> updateReply(@PathVariable Integer rno,
                                                           @RequestBody UpdateReplyRequest request,
                                                           @RequestPart("files") MultipartFile[] files){

        UpdateReplyResponse response = replyService.updateReply(rno, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{rno}")
    @Operation(summary = "특정 댓글을 삭제하는 메소드", description = "삭제할 댓글의 rno를 입력하세요.")
    public ResponseEntity<DeleteReplyResponse> deleteReply(@PathVariable Integer rno) {

        DeleteReplyResponse response = replyService.deleteReply(rno);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}