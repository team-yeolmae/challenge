package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.UpdateChallengeRequest;
import org.yeolmae.challenge.domain.dto.UpdateChallengeResponse;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public CreateChallengeResponse createChallenge(CreateChallengeRequest request) {

        Challenge challenge = Challenge.builder()
                .title(request.getTitle())
                .writer(request.getWriter())
                .content(request.getContent())
                .register_date(request.getRegister_date())
                .start_date(request.getStart_date())
                .end_date(request.getEnd_date())
                .build();

        Challenge savedChallenge = challengeRepository.save(challenge);

        return new CreateChallengeResponse(
                savedChallenge.getChallenge_id(),
                savedChallenge.getTitle(),
                savedChallenge.getWriter(),
                savedChallenge.getContent(),
                savedChallenge.getRegister_date(),
                savedChallenge.getStart_date(),
                savedChallenge.getEnd_date()
        );
    }

    @Transactional
    public UpdateChallengeResponse updateChallenge(Integer challenge_id, UpdateChallengeRequest request) {

        Challenge foundChallenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));
        //Dirty Checking
        foundChallenge.update(request.getTitle(), request.getWriter(), request.getContent(),
                request.getStart_date(), request.getEnd_date());

        return new UpdateChallengeResponse(foundChallenge.getChallenge_id(), foundChallenge.getTitle(), foundChallenge.getWriter(),
                foundChallenge.getContent(), foundChallenge.getRegister_date(), foundChallenge.getStart_date(),
                foundChallenge.getEnd_date());

    }
  
}
