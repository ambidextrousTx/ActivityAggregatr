package com.score.model;

/**
 * Using playerId as key means all events from the same player
 * end up in the same Kafta topic
 *
 * @param playerId
 * @param game
 * @param score
 * @param timestamp
 */
public record ScoreEvent(String playerId, String game, int score, long timestamp) {}

