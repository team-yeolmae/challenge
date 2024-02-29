package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.DeleteReplyResponse;
import org.yeolmae.challenge.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public DeleteReplyResponse deleteReply(Integer rno) {

        Reply reply = replyRepository.findById(rno)
                .orElseThrow(() -> new EntityNotFoundException("해당 rno로 조회된 게시글이 없습니다."));

        replyRepository.delete(reply);

        return new DeleteReplyResponse(reply.getRno(), reply.getReplyer(), reply.getReplyText());
    }

}
