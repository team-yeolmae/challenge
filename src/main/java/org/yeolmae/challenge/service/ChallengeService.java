package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<ReadChallengeResponse> readAll(Pageable pageable) {

        Page<Challenge> challengePage = challengeRepository.findAll(pageable);

        return challengePage.map(challenge -> new ReadChallengeResponse(challenge.getChallenge_id(), challenge.getTitle(),
                challenge.getWriter(), challenge.getContent(), challenge.getRegister_date(), challenge.getStart_date(),
                challenge.getEnd_date()));
    }
}
