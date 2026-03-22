package com.leaderboard.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LeaderboardStore {

    // game -> playerId -> totalScore
    // Kafka and leaderboard-service run on different threads, therefore we need
    // a ConcurrentHashMap
    private final Map<String, Map<String, Integer>> scores = new ConcurrentHashMap<>();

    public void addScore(String game, String playerId, int score) {
        scores
                .computeIfAbsent(game, g -> new ConcurrentHashMap<>())
                .merge(playerId, score, Integer::sum);
    }

    public Map<String, Integer> getLeaderboard(String game) {
        return Collections.unmodifiableMap(
                scores.getOrDefault(game, Collections.emptyMap())
        );
    }

}
