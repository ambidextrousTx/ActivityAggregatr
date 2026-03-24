package com.leaderboard.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "player_scores",
        uniqueConstraints = @UniqueConstraint(columnNames = {"game", "player_id"})
)
public class PlayerScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game", nullable = false)
    private String game;

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(nullable = false)
    private int totalScore;

    // Required by JPA to instantiate entities via reflection
    protected PlayerScore() {}

    public PlayerScore(String game, String playerId, int totalScore) {
        this.game = game;
        this.playerId = playerId;
        this.totalScore = totalScore;
    }

    public String getGame() { return game; }
    public String getPlayerId() { return playerId; }
    public int getTotalScore() { return totalScore; }
    public void addScore(int score) { this.totalScore += score; }
}
