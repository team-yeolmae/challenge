package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yeolmae.challenge.controller.restcontroller.UpDownController;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.DeleteChallengeResponse;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.domain.dto.UpdateChallengeRequest;
import org.yeolmae.challenge.domain.dto.UpdateChallengeResponse;
import org.yeolmae.challenge.domain.dto.CreateChallengeRequest;
import org.yeolmae.challenge.domain.dto.CreateChallengeResponse;
import org.yeolmae.challenge.domain.dto.upload.UploadFileResponse;
import org.yeolmae.challenge.repository.ChallengeRepository;

import java.util.List;

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
                .registerDate(request.getRegisterDate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        Challenge savedChallenge = challengeRepository.save(challenge);

        return new CreateChallengeResponse(
                savedChallenge.getId(),
                savedChallenge.getTitle(),
                savedChallenge.getWriter(),
                savedChallenge.getContent(),
                savedChallenge.getRegisterDate(),
                savedChallenge.getStartDate(),
                savedChallenge.getEndDate()
        );
    }

    @Transactional
    public UpdateChallengeResponse updateChallenge(Integer challenge_id, UpdateChallengeRequest request) {

        Challenge foundChallenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));
        //Dirty Checking
        foundChallenge.update(request.getTitle(), request.getWriter(), request.getContent(),
                request.getStartDate(), request.getEndDate());

        return new UpdateChallengeResponse(foundChallenge.getId(), foundChallenge.getTitle(), foundChallenge.getWriter(),
                foundChallenge.getContent(), foundChallenge.getRegisterDate(), foundChallenge.getStartDate(),
                foundChallenge.getEndDate());
    }

    public Page<ReadChallengeResponse> readAllChallenge(Pageable pageable) {

        Page<Challenge> challengePage = challengeRepository.findAll(pageable);

        return challengePage.map(challenge -> new ReadChallengeResponse(challenge.getId(), challenge.getTitle(),
                challenge.getWriter(), challenge.getContent(), challenge.getRegisterDate(), challenge.getStartDate(),
                challenge.getEndDate()));
    }

    @Transactional
    public DeleteChallengeResponse deleteChallenge(Integer challenge_id) {

        Challenge challenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));

        challengeRepository.delete(challenge);

        return new DeleteChallengeResponse(challenge.getId(), challenge.getTitle(), challenge.getWriter(),
                challenge.getContent(), challenge.getRegisterDate(), challenge.getStartDate(), challenge.getEndDate());
    }

    public ReadChallengeResponse readChallengeById(Integer challenge_id) {

        Challenge foundChallenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challenge_id로 조회된 게시글이 없습니다."));

        return new ReadChallengeResponse(foundChallenge.getId(), foundChallenge.getTitle(), foundChallenge.getWriter(),
                foundChallenge.getContent(), foundChallenge.getRegisterDate(), foundChallenge.getStartDate(),
                foundChallenge.getEndDate());
    }

}
