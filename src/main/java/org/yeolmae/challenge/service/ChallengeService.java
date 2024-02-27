package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ReadChallengeResponse readChallengeById(Integer challenge_id) {

        Challenge foundChallenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));

        return new ReadChallengeResponse(foundChallenge.getChallenge_id(), foundChallenge.getTitle(), foundChallenge.getWriter(),
                foundChallenge.getContent(), foundChallenge.getRegister_date(), foundChallenge.getStart_date(),
                foundChallenge.getEnd_date());
    }

}
