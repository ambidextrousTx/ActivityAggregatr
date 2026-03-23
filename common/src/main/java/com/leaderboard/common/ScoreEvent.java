package com.leaderboard.common;

public record ScoreEvent(String playerId, String game, int score, long timestamp) {}
