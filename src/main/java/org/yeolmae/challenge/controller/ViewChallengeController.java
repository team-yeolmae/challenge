package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Log4j2
public class ViewChallengeController {

    private final ChallengeService challengeService;

        @GetMapping("/main")
        public void list(PageRequest1DTO pageRequest1DTO, Model model){

            PageResponse1DTO<ReadChallengeResponse> challengeDTO =
                    challengeService.readAllChallenge1(pageRequest1DTO);
            // 인기 챌린지 목록 가져오기 (페이지 크기: 4)
            PageResponse1DTO<ReadChallengeResponse> popularChallengesDTO =
                    challengeService.readPopularChallenges(PageRequest1DTO.builder().size(4).build());

            model.addAttribute("challengeDTO", challengeDTO);
            model.addAttribute("popularChallengesDTO", popularChallengesDTO);

            log.info(challengeDTO);

        }

    @GetMapping("/user/detail{id}")
    public String viewChallengeDetail(@PathVariable Integer id, Model model) {

        // id에 해당하는 도전과제 정보를 가져옴
        ReadChallengeResponse challengeDTO = challengeService.readChallengeById(id);

        // 모델에 도전과제 정보를 추가하여 Thymeleaf에서 사용할 수 있도록 함
        model.addAttribute("challengeDTO", challengeDTO);

        // 상세 페이지 템플릿의 경로를 반환
        return "user/detail"; // 이 경로는 상황에 따라 변경될 수 있습니다.
    }
}
