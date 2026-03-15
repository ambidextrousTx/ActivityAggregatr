package com.ravisomsinha.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ScoreSubmission(
        @NotBlank String playerId,
        @NotBlank String game,
        @Min(0) int score) {
}
