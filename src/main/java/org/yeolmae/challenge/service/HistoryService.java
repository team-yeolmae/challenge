package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yeolmae.challenge.domain.History;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.HistoryRepository;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

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