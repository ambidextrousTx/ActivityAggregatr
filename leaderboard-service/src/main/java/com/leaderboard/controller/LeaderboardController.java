package com.leaderboard.controller;

import com.leaderboard.model.PlayerScore;
import com.leaderboard.service.LeaderboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService service;


    public LeaderboardController(LeaderboardService service) {
        this.service = service;
    }

    @GetMapping("/{game}")
    public List<PlayerScore> getLeaderboard(@PathVariable String game) {
        return service.getLeaderboard(game);
    }

}
