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

import java.util.List;

@Controller
@RequestMapping("/admin")
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
            return "redirect:/register";
        }

        log.info(createChallengeRequest);

        CreateChallengeResponse id = challengeService.createChallenge(createChallengeRequest);

        redirectAttributes.addFlashAttribute("result", id);

        return "redirect:/admin/list";
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

            return "redirect:/admin/modify?"+link;
        }

//        // 기존 파일 목록을 가져와서 새로운 파일 목록으로 업데이트
//        List<String> newFileNames = request.getFileNames();
//
//        // 이 부분은 실제 데이터 모델에 맞게 업데이트가 필요한 부분입니다.
//        request.setFileNames(newFileNames);

        challengeService.updateChallenge(id, request);

        log.info("challenge modify post......." + id);
//        log.info("Request received: {}", request);


        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("id", id);

        return "redirect:/admin/read";
    }

    @PostMapping("/delete")
    public String delete(Integer id, RedirectAttributes redirectAttributes) {


        log.info("delete Post..." + id);

        challengeService.deleteChallenge(id);


        redirectAttributes.addFlashAttribute("delete", "deleted");


        return "redirect:/admin/list";

    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<ReadChallengeResponse> challengeDTO =
                challengeService.readAllChallenge(pageRequestDTO);


        model.addAttribute("challengeDTO", challengeDTO);


    }


}
