package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.ReplyImage;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.domain.dto.CreateReplyResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.ReplyRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

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

        // 이미지를 DB에 저장하고 Reply에 추가
        List<ReplyImage> replyImages = new ArrayList<>();

        for(MultipartFile file : request.getImages()) {

            String uuid = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();

            String savedFileName = saveImage(file, uuid, fileName);

            ReplyImage replyImage = ReplyImage.builder()
                    .uuid(uuid)
                    .fileName(fileName)
                    .build();

            replyImages.add(replyImage);
        }

        reply.setImageSet(new HashSet<>(replyImages));

        //
        Reply savedReply = replyRepository.save(reply);

        return new CreateReplyResponse(
                request.getChallengeId(),
                savedReply.getRno(),
                savedReply.getReplyText(),
                savedReply.getReplyer(),
                savedReply.getRegisterDate(),
                savedReply.getImageSet()
        );
    }

    private String saveImage(MultipartFile file, String uuid, String fileName) {

        return uuid + "_" + fileName;
    }


    public ReadReplyResponse readReplyById(Integer rno) {

        Reply foundReply= replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));

        return new ReadReplyResponse(
                foundReply.getChallenge().getId(),
                foundReply.getRno(),
                foundReply.getReplyer(),
                foundReply.getReplyText(),
                foundReply.getRegisterDate(),
                foundReply.getImageSet()
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
                foundReply.getRegisterDate(),
                foundReply.getImageSet()
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
                reply.getChallenge().getId(),
                reply.getRno(),
                reply.getReplyer(),
                reply.getReplyText(),
                reply.getRegisterDate(),
                reply.getImageSet()
            )
        );
    }



}