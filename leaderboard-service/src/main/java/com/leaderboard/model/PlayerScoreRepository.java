package com.leaderboard.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Spring Data reads the method names and generates the SQL at startup
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {
    List<PlayerScore> findByGame(String game);

    Optional<PlayerScore> findByGameAndPlayerId(String game, String playerId);
}
