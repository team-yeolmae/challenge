package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yeolmae.challenge.domain.dto.PageRequestDTO;
import org.yeolmae.challenge.domain.dto.PageResponseDTO;
import org.yeolmae.challenge.domain.dto.ReadChallengeResponse;
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@RequestMapping("/challenge")
@RequiredArgsConstructor
@Log4j2
public class ViewChallengeController {

    private final ChallengeService challengeService;

        @GetMapping("/main")
        public void list(PageRequestDTO pageRequestDTO, Model model){

            PageResponseDTO<ReadChallengeResponse> challengeDTO =
                    challengeService.readAllChallenge(pageRequestDTO);


            model.addAttribute("challengeDTO", challengeDTO);

        }

    @GetMapping("/detail{id}")
    public String viewChallengeDetail(@PathVariable Integer id, Model model) {

        // id에 해당하는 도전과제 정보를 가져옴
        ReadChallengeResponse challengeDTO = challengeService.readChallengeById(id);

        // 모델에 도전과제 정보를 추가하여 Thymeleaf에서 사용할 수 있도록 함
        model.addAttribute("challengeDTO", challengeDTO);

        // 상세 페이지 템플릿의 경로를 반환
        return "challenge/detail"; // 이 경로는 상황에 따라 변경될 수 있습니다.
    }
}
