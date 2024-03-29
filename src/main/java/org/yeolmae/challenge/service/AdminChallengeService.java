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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class AdminChallengeService {

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

        if(request.getFileNames() != null){
            request.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                challenge.addChallengeImage(arr[0], arr[1]);
            });
        }


        Challenge savedChallenge = challengeRepository.save(challenge);

        List<String> fileNames = savedChallenge.getImageSet().stream()
                .sorted()
                .map(challengeImage -> challengeImage.getImage_detail() + "_" + challengeImage.getImage_thumb())
                .collect(Collectors.toList());

        return new CreateChallengeResponse(
                savedChallenge.getId(),
                savedChallenge.getTitle(),
                savedChallenge.getContent(),
                savedChallenge.getWriter(),
                savedChallenge.getRegisterDate(),
                savedChallenge.getStartDate(),
                savedChallenge.getEndDate(),
                fileNames
        );
    }

    public ReadChallengeResponse readOneChallenge(Integer id) {

        //challenge_image 까지 조인 처리되는 findByWithImages()를 이용
        Optional<Challenge> result = challengeRepository.findByIdWithImages(id);

        Challenge challenge =result.orElseThrow();

        ReadChallengeResponse response = ReadChallengeResponse.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .writer(challenge.getWriter())
                .registerDate(challenge.getRegisterDate())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .build();

        List<String> fileNames = challenge.getImageSet().stream()
                .sorted()
                .map(challengeImage -> challengeImage.getImage_detail() + "_" + challengeImage.getImage_thumb())
                .collect(Collectors.toList());

        response.setFileNames(fileNames);

        return response;

    }

    @Transactional
    public void updateChallenge(Integer id, UpdateChallengeRequest request) {

        Optional<Challenge> result = challengeRepository.findById(id);

        Challenge foundChallenge = result
                .orElseThrow(() -> new EntityNotFoundException("해당 id로 조회된 게시물이 없습니다."));

//        Challenge challenge = foundChallenge.orElseThrow(() -> new EntityNotFoundException("해당 id로 조회된 게시글이 없습니다."));

        //Dirty Checking
        foundChallenge.update(request.getTitle(),
                request.getContent(),
                request.getWriter(),
                request.getStartDate(),
                request.getEndDate()
        );

        foundChallenge.clearChallengeImage();

        if(request.getFileNames() != null){
            for (String fileName : request.getFileNames()) {
                String[] arr = fileName.split("_");
                foundChallenge.addChallengeImage(arr[0], arr[1]);
            }
        }

        log.info("Request received: {}", request);

        challengeRepository.save(foundChallenge);

    }
    @Transactional
    public void deleteChallenge(Integer id) {

        challengeRepository.deleteById(id);

        log.info("service id: {}", id);

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