package org.yeolmae.challenge.controller.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.service.ChallengeService;
import org.yeolmae.challenge.service.MemberService;

import java.util.Map;

@RestController
@RequestMapping("user/api/challenge")
@RequiredArgsConstructor
@Log4j2
public class ChallengeController {

    private final MemberService memberService;
    private final ChallengeService challengeService;

//    private final ChallengeService challengeService;
//
//    @GetMapping("/{challenge_id}")
//    public ResponseEntity<ReadChallengeResponse> challengeRead(@PathVariable Integer challenge_id) {
//
//        ReadChallengeResponse response = challengeService.readChallengeById(challenge_id);
//
//        return  new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<CreateChallengeResponse> challengeCreate(@RequestBody CreateChallengeRequest request) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String username = userDetails.getUsername();
//
//            System.out.println(username);
//        } else {
//            System.out.println("No authenticated user");
//        }
//
//        CreateChallengeResponse response = challengeService.createChallenge(request);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @PutMapping("/{challenge_id}")
//    public ResponseEntity<UpdateChallengeResponse> challengeUpdate(@PathVariable Integer challenge_id,
//                                                                   @RequestBody UpdateChallengeRequest request){
//
//        UpdateChallengeResponse response = challengeService.updateChallenge(challenge_id, request);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<ReadChallengeResponse>> challengeReadAll(@PageableDefault(
//            size = 5, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
//
//        Page<ReadChallengeResponse> response = challengeService.readAllChallenge(pageable);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{challenge_id}")
//    public ResponseEntity<DeleteChallengeResponse> challengeDelete(@PathVariable Integer challenge_id) {
//
//        DeleteChallengeResponse response = challengeService.deleteChallenge(challenge_id);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping("/participate")
    public ResponseEntity<?> participateInChallenge(@RequestBody ChallengeParticipateRequest request){

        log.info("🐱‍👤 method 요청됨.");
        int memberID = memberService.getMember().getId();

        boolean success = challengeService.participateInChallenge(memberID, request.getChallengeId());

        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "참가가 완료되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message","참가에 실패하였습니다."));
        }
    }

}