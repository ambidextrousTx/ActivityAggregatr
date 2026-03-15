package com.ravisomsinha.controller;

import com.ravisomsinha.model.ScoreSubmission;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    @PostMapping
    public ResponseEntity<String> submitScore(
            @Valid @RequestBody ScoreSubmission scoreSubmission
            ) {
        return ResponseEntity.ok("Score submitted: " + scoreSubmission.playerId());
    }
}
