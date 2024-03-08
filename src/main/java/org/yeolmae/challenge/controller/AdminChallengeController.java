package org.yeolmae.challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yeolmae.challenge.domain.dto.*;
import org.yeolmae.challenge.service.AdminChallengeService;

@Controller
@RequestMapping("/challenge/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminChallengeController {

    private final AdminChallengeService challengeService;

    @GetMapping("/register")
    public void registerGET(){
    }
    @PostMapping("/register")
    public String registerChallenge(@Valid CreateChallengeRequest createChallengeRequest, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        log.info("challenge POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            log.info("Errors: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/challenge/register";
        }

        log.info(createChallengeRequest);

        CreateChallengeResponse id = challengeService.createChallenge(createChallengeRequest);

        redirectAttributes.addFlashAttribute("result", id);

        return "redirect:/challenge/admin/list";
    }

    @GetMapping({"/read", "/modify"})
    public void readOneChallenge(Integer id, PageRequestDTO pageRequestDTO, Model model){

        ReadChallengeResponse response = challengeService.readOneChallenge(id);

        log.info(response);

        model.addAttribute("dto", response);

    }

    @PostMapping("/modify")
    public String modifyChallenge(@Valid Integer id, @Valid UpdateChallengeRequest request,
                          BindingResult bindingResult,
                          PageRequestDTO pageRequestDTO,
                          RedirectAttributes redirectAttributes){


        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("id", id);

            return "redirect:/challenge/admin/modify?"+link;
        }

        challengeService.updateChallenge(id, request);

        log.info("challenge modify post......." + id);
//        log.info("Request received: {}", request);


        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("id", id);

        return "redirect:/challenge/admin/read";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<ReadChallengeResponse> challengeDTO =
                challengeService.readAllChallenge(pageRequestDTO);


        model.addAttribute("challengeDTO", challengeDTO);


    }


}
