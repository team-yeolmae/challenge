package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.ReplyImage;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.ReplyRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        // 요청에서 전달된 이미지들을 댓글에 추가
        for (ReplyImage replyImage : request.getReplyImages()) {
            reply.addImage(replyImage.getUuid(), replyImage.getFileName());
        }

        Reply savedReply = replyRepository.save(reply);

        // 댓글에 첨부된 이미지들을 배열로 변환
        ReplyImage[] replyImages = savedReply.getImageSet().toArray(new ReplyImage[0]);

        return new CreateReplyResponse(
                request.getChallengeId(),
                savedReply.getRno(),
                savedReply.getReplyText(),
                savedReply.getReplyer(),
                savedReply.getRegisterDate(),
                replyImages
        );
    }


    public ReadReplyResponse readReplyById(Integer rno) {

        Reply foundReply= replyRepository.findByIdWithImage(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));

        // 댓글에 첨부된 이미지들을 배열로 변환
        ReplyImage[] replyImages = foundReply.getImageSet().toArray(new ReplyImage[0]);

        return new ReadReplyResponse(
                foundReply.getRno(),
                foundReply.getReplyer(),
                foundReply.getReplyText(),
                foundReply.getRegisterDate(),
                replyImages
        );
    }


    @Transactional
    public UpdateReplyResponse updateReply(Integer rno, UpdateReplyRequest request) {

        Reply foundReply = replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));
        //Dirty Checking
        foundReply.changeReply(request.getReplyText());   // 댓글 내용만 수정

        // 기존 첨부된 이미지 삭제
        foundReply.clearImage();

        // 새로운 이미지 추가
        for (ReplyImage replyImage : request.getReplyImages()) {
            foundReply.addImage(replyImage.getUuid(), replyImage.getFileName());
        }

        // 댓글에 첨부된 이미지들을 배열로 변환
        ReplyImage[] replyImages = foundReply.getImageSet().toArray(new ReplyImage[0]);

        return new UpdateReplyResponse(
                foundReply.getChallenge().getId(),
                foundReply.getRno(),
                foundReply.getReplyer(),
                foundReply.getReplyText(),
                foundReply.getRegisterDate(),
                replyImages
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

        // 각 댓글과 댓글의 첫번째 이미지 객체 가져오기
        return replyPage.map(reply -> {

            Set<ReplyImage> imageSet = reply.getImageSet();

            ReplyImage firstImage = null;
            String firstImageName = "";

            if(imageSet != null && !imageSet.isEmpty()) {
                firstImage = imageSet.iterator().next();
                firstImageName = firstImage.getUuid() + "_" + firstImage.getFileName();

            } else {
                return null;
            }

            // 첫 번째 이미지를 배열로 감싸기
            ReplyImage[] firstImageArray = (firstImage != null) ? new ReplyImage[]{firstImage} : new ReplyImage[0];


            return new ReadReplyResponse(
                    reply.getRno(),
                    reply.getReplyText(),
                    reply.getReplyer(),
                    reply.getRegisterDate(),
                    firstImageArray
            );

        });


    }


}
