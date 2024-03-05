package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
