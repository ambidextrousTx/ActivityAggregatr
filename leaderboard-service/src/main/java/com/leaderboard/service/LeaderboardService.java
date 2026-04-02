package com.leaderboard.service;

import com.leaderboard.common.ScoreEvent;
import com.leaderboard.model.PlayerScore;
import com.leaderboard.model.PlayerScoreRepository;
import com.leaderboard.model.ProcessedEvent;
import com.leaderboard.model.ProcessedEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaderboardService {
    private final PlayerScoreRepository repository;
    private final ProcessedEventRepository processedEventRepository;

    public LeaderboardService(
            PlayerScoreRepository playerScoreRepository,
            ProcessedEventRepository processedEventRepository
    ) {
        this.repository = playerScoreRepository;
        this.processedEventRepository = processedEventRepository;
    }

    @Transactional
    public void applyScore(ScoreEvent event, String eventId) {
        if (processedEventRepository.existsById(eventId)) {
            System.out.println("Duplicate event, skipping: " + eventId);
            return;
        }

        repository.findByGameAndPlayerId(event.game(), event.playerId())
                .ifPresentOrElse(
                        existing -> existing.addScore(event.score()),
                        () -> repository.save(
                                new PlayerScore(event.game(), event.playerId(), event.score())
                        )
                );

        processedEventRepository.save(new ProcessedEvent(eventId));
    }

    public List<PlayerScore> getLeaderboard(String game) {
        return repository.findByGame(game);
    }
}
