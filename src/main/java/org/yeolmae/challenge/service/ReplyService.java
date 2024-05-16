package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.domain.dto.CreateReplyResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.MemberRepository;
import org.yeolmae.challenge.repository.ReplyRepository;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ChallengeRepository challengeRepository;

    @Autowired
    private MemberService memberService;


    @Transactional
    public CreateReplyResponse createReply(CreateReplyRequest request) {

        int challengeId = request.getChallengeId();

        // 인증객체를 통한 로그인 사용자 식별
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            System.out.println("🐱‍🚀 username : " + username);

        } else {
            System.out.println("👻No authenticated user");
        }

        Member member = memberService.getMember();
        log.info("✅found member : " + member);

        // 챌린지 정보 식별
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("요청하신 챌린지 정보를 찾을 수 없습니다."));

        // reply build
        Reply reply = Reply.builder()
                .challenge(challenge)
                .member(member)
                .replyText(request.getReplyText())
                .replyer(request.getReplyer())
                .registerDateTime(request.getRegisterDateTime())
                .build();

        // reply 저장
        Reply savedReply = replyRepository.save(reply);

        // replyDTO 생성
        return new CreateReplyResponse(
                request.getChallengeId(),
                savedReply.getRno(),
                savedReply.getReplyText(),
                savedReply.getMember().getNickname(),
                savedReply.getRegisterDateTime(),
                savedReply.getModifyDateTime()
        );
    }


//    public ReadReplyResponse readReplyById(Integer rno) {
//
//        Reply foundReply= replyRepository.findById(rno)
//                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));
//
//        return new ReadReplyResponse(
//                foundReply.getRno(),
//                foundReply.getReplyer(),
//                foundReply.getReplyText(),
//                foundReply.getRegisterDate()
//        );
//    }


    public Page<ReadReplyResponse> readAllReplies(int challengeId, Pageable pageable) {

        Page<Reply> replyPage = replyRepository.listOfReplies(challengeId, pageable);

        return replyPage.map(reply -> new ReadReplyResponse(
                reply.getRno(),
                reply.getReplyText(),
                reply.getReplyer(),
                reply.getRegisterDateTime(),
                reply.getModifyDateTime()
        ));
    }


    @Transactional
    public UpdateReplyResponse updateReply(Integer rno, UpdateReplyRequest request) {

        Reply foundReply = replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));

        //Dirty Checking
        foundReply.changeReply(request.getReplyText());   // 댓글 내용 수정, 수정일 추가

        Reply updatedReply = replyRepository.save(foundReply);

        return new UpdateReplyResponse(
                foundReply.getChallenge().getId(),
                foundReply.getRno(),
                foundReply.getReplyer(),
                foundReply.getReplyText(),
                foundReply.getRegisterDateTime(),
                foundReply.getModifyDateTime()
        );

    }


    @Transactional
    public DeleteReplyResponse deleteReply(Integer rno) {

        Reply reply = replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));

        replyRepository.delete(reply);

        return new DeleteReplyResponse(
                reply.getChallenge().getId(),
                reply.getRno(),
                reply.getReplyer(),
                reply.getReplyText()
        );
    }

}