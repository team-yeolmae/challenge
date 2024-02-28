package org.yeolmae.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeolmae.challenge.domain.Level;
import org.yeolmae.challenge.service.BadgeService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("/{level}")
    public ResponseEntity<Level> getUserLevel(@PathVariable Integer level) {

        Level user = badgeService.assignBadge(level);

        if (user != null) {

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
