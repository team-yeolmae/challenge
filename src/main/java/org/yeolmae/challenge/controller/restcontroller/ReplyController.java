package org.yeolmae.challenge.controller.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.domain.dto.CreateReplyResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.service.ChallengeService;
import org.yeolmae.challenge.service.ReplyService;

import java.util.Arrays;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final ChallengeService challengeService;

    @PostMapping("/register")
    @Operation(summary = "댓글을 등록하는 메소드", description = "text를 입력하세요.")
    public ResponseEntity<CreateReplyResponse> createReply(
            @ModelAttribute CreateReplyRequest request, @RequestPart("files") MultipartFile[] files) {

        request.setImages(Arrays.asList(files));

        CreateReplyResponse response = replyService.createReply(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 목록 10개씩 paging 처리
    @GetMapping("/list/{challengeId}")
    @Operation(summary = "댓글 목록을 조회하는 메소드", description = "댓글 목록을 조회할 challengeId와 page를 입력하세요.")
    public ResponseEntity<Page<ReadReplyResponse>> readAllReply(
            @PathVariable("challengeId") int challengeId,
            @PageableDefault(size = 10, sort = "rno", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReadReplyResponse> response = replyService.readAllReplies(challengeId, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{rno}")
    @Operation(summary = "특정 댓글을 조회하는 메소드", description = "조회할 댓글의 rno를 입력하세요.")
    public ResponseEntity<ReadReplyResponse> readReply(@PathVariable("rno") Integer rno) {

        ReadReplyResponse response = replyService.readReplyById(rno);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    @Operation(summary = "특정 댓글을 수정하는 메소드", description = "수정할 댓글의 rno와 수정 text를 입력하세요.")
    public ResponseEntity<UpdateReplyResponse> updateReply(@PathVariable Integer rno,
                                                           @RequestBody UpdateReplyRequest request){

        UpdateReplyResponse response = replyService.updateReply(rno, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    @Operation(summary = "특정 댓글을 삭제하는 메소드", description = "삭제할 댓글의 rno를 입력하세요.")
    public ResponseEntity<DeleteReplyResponse> deleteReply(@PathVariable Integer rno) {

        DeleteReplyResponse response = replyService.deleteReply(rno);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}