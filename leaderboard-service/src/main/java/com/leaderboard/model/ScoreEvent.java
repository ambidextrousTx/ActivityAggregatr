package com.leaderboard.model;

// to be extracted into a shared module because right now
// it is duplicated from score-service
public record ScoreEvent(String playerId, String game, int score, long timestamp) {}
