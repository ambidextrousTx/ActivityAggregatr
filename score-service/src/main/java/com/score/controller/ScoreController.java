package com.score.controller;

import com.leaderboard.common.ScoreEvent;
import com.score.model.ScoreSubmission;
import com.score.service.ScoreProducer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreProducer scoreProducer;

    public ScoreController(ScoreProducer scoreProducer) {
        this.scoreProducer = scoreProducer;
    }

    @PostMapping
    public ResponseEntity<String> submitScore(
            @Valid @RequestBody ScoreSubmission scoreSubmission
            ) {

        ScoreEvent event = new ScoreEvent(
                scoreSubmission.playerId(),
                scoreSubmission.game(),
                scoreSubmission.score(),
                System.currentTimeMillis()
        );

        scoreProducer.publishScore(event);

        return ResponseEntity.ok("Score submitted for player: " + scoreSubmission.playerId());
    }
}
