package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional
    public CreateChallengeResponse createPost(CreateChallengeRequest request) {

        Challenge post = Challenge.builder()
                .title(request.getTitle())
                .writer(request.getWriter())
                .content(request.getContent())
                .register_date(request.getRegister_date())
                .start_date(request.getStart_date())
                .end_date(request.getEnd_date())
                .build();

        Challenge savedPost = challengeRepository.save(post);

        return new CreateChallengeResponse(savedPost.getChallenge_id(), savedPost.getTitle(), savedPost.getWriter(),
                savedPost.getContent(), savedPost.getRegister_date(), savedPost.getStart_date(), savedPost.getEnd_date());
    }

    public ReadChallengeResponse readPostById(Integer challenge_id) {

        Challenge foundPost = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));

        return new ReadChallengeResponse(foundPost.getChallenge_id(), foundPost.getTitle(), foundPost.getWriter(),
                foundPost.getContent(), foundPost.getRegister_date(), foundPost.getStart_date(), foundPost.getEnd_date());
    }
}
