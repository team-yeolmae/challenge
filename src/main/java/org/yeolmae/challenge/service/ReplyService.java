package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.domain.dto.ReadReplyResponse;
import org.yeolmae.challenge.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public CreateReplyResponse createReply(CreateReplyRequest request) {

        Reply reply = Reply.builder()
                .replyer(request.getReplyer())
                .replyText(request.getReplyText())
                .build();

        Reply savedReply = replyRepository.save(reply);

        return new CreateReplyResponse(
                savedReply.getRno(),
                savedReply.getReplyText(),
                savedReply.getReplyer()
        );
    }
  
}
