package org.yeolmae.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Reply;
import org.yeolmae.challenge.domain.dto.ReadReplyResponse;
import org.yeolmae.challenge.repository.ReadRepository;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReadRepository repository;

    public Page<ReadReplyResponse> readAllReply(Pageable pageable) {

        Page<Reply> replyPage = repository.findAll(pageable);

        return replyPage.map(reply -> new ReadReplyResponse(reply.getRno(), reply.getReplyer(), reply.getReplyText()));
    }

}
