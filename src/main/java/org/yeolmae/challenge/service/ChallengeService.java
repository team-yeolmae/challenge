package org.yeolmae.challenge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Challenge;
import org.yeolmae.challenge.domain.History;
import org.yeolmae.challenge.domain.Member;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.repository.ChallengeRepository;
import org.yeolmae.challenge.repository.HistoryRepository;
import org.yeolmae.challenge.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final MemberRepository memberRepository;
    private final HistoryRepository historyRepository;

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
                savedChallenge.getEndDate(),
                savedChallenge.getImageSet()
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

    //view
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

    public PageResponse1DTO<ReadChallengeResponse> readAllChallenge1(PageRequest1DTO pageRequest1DTO) {
        String[] types = pageRequest1DTO.getTypes();
        String keyword = pageRequest1DTO.getKeyword();
        Pageable pageable = pageRequest1DTO.getPageable("id");  // 수정

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

        return PageResponse1DTO.<ReadChallengeResponse>withAll()  // 수정
                .pageRequest1DTO(pageRequest1DTO)
                .challengeList(challengeList)  // 수정
                .total((int) result.getTotalElements())  // 수정
                .build();
    }
    public PageResponse1DTO<ReadChallengeResponse> readPopularChallenges(PageRequest1DTO pageRequest1DTO) {
        String[] types = pageRequest1DTO.getTypes();
        String keyword = pageRequest1DTO.getKeyword();
        Pageable pageable = pageRequest1DTO.getPageable("id");  // 수정

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

        return PageResponse1DTO.<ReadChallengeResponse>withAll()  // 수정
                .pageRequest1DTO(pageRequest1DTO)
                .challengeList(challengeList)  // 수정
                .total((int) result.getTotalElements())  // 수정
                .build();
    }

    @Transactional
    public boolean participateInChallenge(int memberId, int challengeId){
        // 사용자와 챌린지 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("Invalid member ID"));
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(()->new IllegalArgumentException("Invalid challenge ID"));

        // 이미 참여했는지 확인
        boolean alreadyParticipated = historyRepository.existsByMemberAndChallenge(member, challenge);
        if (alreadyParticipated){
            return false; //이미 참여한 경우, 참여 불가 처리
        }

        // 새로운 참여 기록 생성
        History history = new History();
        history.setMember(member);
        history.setChallenge(challenge);
        history.setSuccess(false); // 성공여부 초기값 설정, 참여 후 성공 여부 별도로 업데이트 로직
        historyRepository.save(history);

        // 참여 성공
        return true;
    }
}

