package com.leaderboard.service;

import com.leaderboard.common.ScoreEvent;
import com.leaderboard.model.PlayerScore;
import com.leaderboard.model.PlayerScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaderboardService {
    private final PlayerScoreRepository repository;

    public LeaderboardService(PlayerScoreRepository playerScoreRepository) {
        this.repository = playerScoreRepository;
    }

    @Transactional
    public void applyScore(ScoreEvent event) {
        repository.findByGameAndPlayerId(event.game(), event.playerId())
                .ifPresentOrElse(
                        existing -> existing.addScore(event.score()),
                        () -> repository.save(
                                new PlayerScore(event.game(), event.playerId(), event.score())
                        )
                );
    }

    public List<PlayerScore> getLeaderboard(String game) {
        return repository.findByGame(game);
    }
}
