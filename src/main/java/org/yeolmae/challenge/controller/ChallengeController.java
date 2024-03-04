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
import org.yeolmae.challenge.service.ChallengeService;

@Controller
@RequestMapping("/challenge")
@RequiredArgsConstructor
@Log4j2
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/register")
    public void registerGET(){
    }
    @PostMapping("/register")
    public String registerChallenge(@Valid CreateChallengeRequest createChallengeRequest, BindingResult bindingResult, Model model) {
        log.info("challenge POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            log.info("Errors: {}", bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/challenge/register";
        }

        log.info(createChallengeRequest);

        CreateChallengeResponse id = challengeService.createChallenge(createChallengeRequest);

        model.addAttribute("result", id);

        return "redirect:/challenge/list";
    }

    //    @GetMapping("/{challenge_id}")
//    public ResponseEntity<ReadChallengeResponse> challengeRead(@PathVariable Integer challenge_id) {
//
//        ReadChallengeResponse response = challengeService.readChallengeById(challenge_id);
//
//        return  new ResponseEntity<>(response, HttpStatus.OK);
//    }


//    @PutMapping("/{challenge_id}")
//    public ResponseEntity<UpdateChallengeResponse> challengeUpdate(@PathVariable Integer challenge_id,
//                                                                   @RequestBody UpdateChallengeRequest request){
//
//        UpdateChallengeResponse response = challengeService.updateChallenge(challenge_id, request);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//
//    @DeleteMapping("/{challenge_id}")
//    public ResponseEntity<DeleteChallengeResponse> challengeDelete(@PathVariable Integer challenge_id) {
//
//        DeleteChallengeResponse response = challengeService.deleteChallenge(challenge_id);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<ReadChallengeResponse> challengeDTO =
                challengeService.readAllChallenge(pageRequestDTO);


        model.addAttribute("challengeDTO", challengeDTO);


    }


}
