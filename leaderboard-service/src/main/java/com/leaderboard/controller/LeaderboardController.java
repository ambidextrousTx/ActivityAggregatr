package com.leaderboard.controller;

import com.leaderboard.model.LeaderboardStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardStore leaderboardStore;


    public LeaderboardController(LeaderboardStore leaderboardStore) {
        this.leaderboardStore = leaderboardStore;
    }

    @GetMapping("/{game}")
    public Map<String, Integer> getLeaderboard(@PathVariable String game) {
        return leaderboardStore.getLeaderboard(game);
    }

}
