package org.yeolmae.challenge.controller.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.config.auth.PrincipalDetailsService;
import org.yeolmae.challenge.config.auth.Token;
import org.yeolmae.challenge.domain.History;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.service.HistoryService;
import org.yeolmae.challenge.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class MypageController {

    @Autowired
    private final HistoryService historyService;

    @Autowired
    private final MemberService memberService;

    private void saveContext(String username) {
        Token token = new Token(username);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
    }


    @GetMapping("/api/mypage")
    public ResponseEntity<MypageResponse> getMypage(@PageableDefault(
            size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        Member member = memberService.getMember();

        String nickname = member.getNickname();
        int level = member.getLevel();

        Page<HistoryResponse> historyPage = historyService.readAllHistoryById(pageable, member.getId());
        List<HistoryResponse> historyList = historyPage.getContent();

        MypageResponse response = new MypageResponse(nickname, level, historyList);

        log.info(response);
        System.out.println(response);
//        model.addAttribute("historyList", historyList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


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
//        CreateChallengeResponse response = challengeService.createChallenge(request);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


//    @PutMapping("/{challenge_id}")
//    public ResponseEntity<UpdateChallengeResponse> challengeUpdate(@PathVariable Integer challenge_id,
//                                                                   @RequestBody UpdateChallengeRequest request){
//
//        UpdateChallengeResponse response = challengeService.updateChallenge(challenge_id, request);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


//    @DeleteMapping("/{challenge_id}")
//    public ResponseEntity<DeleteChallengeResponse> challengeDelete(@PathVariable Integer challenge_id) {
//
//        DeleteChallengeResponse response = challengeService.deleteChallenge(challenge_id);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


}