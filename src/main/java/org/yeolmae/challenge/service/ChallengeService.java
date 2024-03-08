package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.ChallengeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional
    public CreateChallengeResponse createChallenge(CreateChallengeRequest request) {

        Challenge challenge = Challenge.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .registerDate(request.getRegisterDate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        Challenge savedChallenge = challengeRepository.save(challenge);


        return new CreateChallengeResponse(
                savedChallenge.getId(),
                savedChallenge.getTitle(),
                savedChallenge.getContent(),
                savedChallenge.getWriter(),
                savedChallenge.getRegisterDate(),
                savedChallenge.getStartDate(),
                savedChallenge.getEndDate()
        );
    }

    public ReadChallengeResponse readOneChallenge(Integer id) {

        //challenge_image 까지 조인 처리되는 findByWithImages()를 이용
        //Optional<Challenge> result = challengeRepository.findByIdWithImages(id);

        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 challengeId로 조회된 게시글이 없습니다."));

        return new ReadChallengeResponse(challenge.getId(), challenge.getTitle(), challenge.getContent(), challenge.getWriter(),
                challenge.getRegisterDate(), challenge.getStartDate(), challenge.getEndDate());

    }

    @Transactional
    public void updateChallenge(Integer id, UpdateChallengeRequest request) {

        Challenge foundChallenge = challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id로 조회된 게시물이 없습니다."));

//        Challenge challenge = foundChallenge.orElseThrow(() -> new EntityNotFoundException("해당 id로 조회된 게시글이 없습니다."));

        //Dirty Checking
        foundChallenge.update(request.getTitle(),
                request.getContent(),
                request.getWriter(),
                request.getStartDate(),
                request.getEndDate()
        );

        log.info("Request received: {}", request);

        challengeRepository.save(foundChallenge);

    }

    public PageResponseDTO<ReadChallengeResponse> readAllChallenge(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<Challenge> result = challengeRepository.findAll(pageable);

        List<ReadChallengeResponse> challengeList = new ArrayList<>();
        for (Challenge challenge : result.getContent()) {
            ReadChallengeResponse response = new ReadChallengeResponse();
            response.setId(challenge.getId());
            response.setTitle(challenge.getTitle());
            response.setWriter(challenge.getWriter());
            response.setContent(challenge.getContent());
            response.setRegisterDate(challenge.getRegisterDate());
            response.setStartDate(challenge.getStartDate());
            response.setEndDate(challenge.getEndDate());

            challengeList.add(response);
        }

        return PageResponseDTO.<ReadChallengeResponse>withAll()
                .pageRequestDTO(pageRequestDTO)
                .challengeList(challengeList)
                .total((int)result.getTotalElements())
                .build();

    }

}
