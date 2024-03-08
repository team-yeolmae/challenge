package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public CreateReplyResponse createReply(CreateReplyRequest request) {

        int challengeId = request.getChallengeId();

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("요청하신 챌린지 정보를 찾을 수 없습니다."));

        Reply reply = Reply.builder()
                .challenge(challenge)
                .replyer(request.getReplyer())
                .replyText(request.getReplyText())
                .registerDate(request.getRegisterDate())
                .build();

        Reply savedReply = replyRepository.save(reply);

        return new CreateReplyResponse(
                request.getChallengeId(),
                savedReply.getRno(),
                savedReply.getReplyText(),
                savedReply.getReplyer(),
                savedReply.getRegisterDate()
        );
    }

    public ReadReplyResponse readReplyById(Integer rno) {

        Reply foundReply= replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));

        return new ReadReplyResponse(
                foundReply.getRno(),
                foundReply.getReplyer(),
                foundReply.getReplyText(),
                foundReply.getRegisterDate()
        );
    }

    @Transactional
    public UpdateReplyResponse updateReply(Integer rno, UpdateReplyRequest request) {

        Reply foundReply = replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));
        //Dirty Checking
        foundReply.changeReply(request.getReplyText());   // 댓글 내용만 수정

        return new UpdateReplyResponse(
                foundReply.getChallenge().getId(),
                foundReply.getRno(),
                foundReply.getReplyer(),
                foundReply.getReplyText(),
                foundReply.getRegisterDate()
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

    public Page<ReadReplyResponse> readAllReplies(int challengeId, Pageable pageable) {

        Page<Reply> replyPage = replyRepository.listOfReplies(challengeId, pageable);

        return replyPage.map(reply -> new ReadReplyResponse(
                        reply.getRno(),
                        reply.getReplyText(),
                        reply.getReplyer(),
                        reply.getRegisterDate()
                )
        );
    }

}