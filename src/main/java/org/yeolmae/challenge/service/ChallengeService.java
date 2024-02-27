package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.UpdateChallengeRequest;
import org.yeolmae.challenge.domain.dto.UpdateChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional
    public UpdateChallengeResponse ChallengeUpdate(Integer challenge_id, UpdateChallengeRequest request) {

        Challenge foundPost = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));
        //Dirty Checking
        foundPost.update(request.getTitle(), request.getWriter(), request.getContent());

        return new UpdateChallengeResponse(foundPost.getChallenge_id(), foundPost.getTitle(), foundPost.getWriter(),
                foundPost.getContent(), foundPost.getRegister_date(), foundPost.getStart_date(), foundPost.getEnd_date());

    }



}
