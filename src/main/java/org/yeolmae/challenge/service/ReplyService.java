package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public UpdateReplyResponse replyUpdate(Integer rno, UpdateReplyRequest request) {

        Reply foundReply = replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));
        //Dirty Checking
        foundReply.changeReply(request.getReplyer(), request.getReplyText());

        return new UpdateReplyResponse(foundReply.getRno(), foundReply.getReplyer(), foundReply.getReplyText());

    }

}
