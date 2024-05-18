package org.yeolmae.challenge.controller.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeolmae.challenge.service.HistoryService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
@Log4j2
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping("/joinChallenge")
    public ResponseEntity<Map<String, Object>> joinChallenge(@RequestBody Map<String, Integer> request) {

        int challengeId = request.get("challengeId");
        Map<String, Object> response = new HashMap<>();

        try {
            historyService.addChallengeToHistory(challengeId);
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
