package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.History;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.HistoryRepository;
import org.yeolmae.challenge.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ChallengeRepository challengeRepository;
    private final MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Transactional
    public void addChallengeToHistory(int challengeId) {
        // security context에서 사용자 정보 찾기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("🍎 authentication name : " + authentication.getName());

        if(authentication == null || !authentication.isAuthenticated()) {
            log.info("인증 객체를 찾을 수 없습니다.");
        }

        String username = authentication.getName();

        Member accessMember = memberRepository.findMemberByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("😥해당 memberId로 조회된 회원 정보가 없습니다."));
        
        // 챌린지 정보 가져오기
        Challenge foundChallenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("챌린지 정보를 찾을 수 없습니다."));

        History history = History.builder()
                .member(accessMember)
                .challenge(foundChallenge)
                .success(false)
                .build();

        historyRepository.save(history);
    }

    public Page<HistoryResponse> readAllHistoryById(Pageable pageable, int memberId) {


        Page<History> historyPage = historyRepository.findAll(pageable);

        return historyPage.map(history -> new HistoryResponse(
                history.getId(),
                history.getChallenge(),
                history.isSuccess()
        ));
    }


//    @Transactional
//    public DeleteHistoryResponse deleteHistory(Integer History_id) {
//
//        History History = historyRepository.findById(challenge_id)
//                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));
//
//        challengeRepository.delete(challenge);
//
//        return new DeleteHistoryResponse(challenge.getId(), challenge.getTitle(), challenge.getWriter(),
//                challenge.getContent(), challenge.getRegisterDate(), challenge.getStartDate(), challenge.getEndDate());
//    }

//    public ReadHistoryResponse readHistoryById(Integer challenge_id) {
//
//        History foundHistory = challengeRepository.findById(challenge_id)
//                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));
//
//        return new ReadHistoryResponse(foundHistory.getId(), foundHistory.getTitle(), foundHistory.getWriter(),
//                foundHistory.getContent(), foundHistory.getRegisterDate(), foundHistory.getStartDate(),
//                foundHistory.getEndDate());
//    }

}