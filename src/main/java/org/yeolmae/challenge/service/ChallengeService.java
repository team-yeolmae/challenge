package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.DeleteChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional
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
    public DeleteChallengeResponse deleteChallenge(Integer challenge_id) {

        Challenge challenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));

        challengeRepository.delete(challenge);

        return new DeleteChallengeResponse(challenge.getChallenge_id(), challenge.getTitle(), challenge.getWriter(),
                challenge.getContent(), challenge.getRegister_date(), challenge.getStart_date(), challenge.getEnd_date());
    }

}
