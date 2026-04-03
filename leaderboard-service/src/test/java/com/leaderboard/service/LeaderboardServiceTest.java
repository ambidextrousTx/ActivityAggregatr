package com.leaderboard.service;

import com.leaderboard.common.ScoreEvent;
import com.leaderboard.model.PlayerScoreRepository;
import com.leaderboard.model.ProcessedEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaderboardServiceTest {

    @Mock
    PlayerScoreRepository scoreRepository;

    @Mock
    ProcessedEventRepository processedEventRepository;

    @InjectMocks
    LeaderboardService leaderboardService;

    @Test
    void newScore_isPersistedCorrectly() {
        var event = new ScoreEvent("alice", "tetris", 100, System.currentTimeMillis());
        when(processedEventRepository.existsById("evt-1")).thenReturn(false);
        when(scoreRepository.findByGameAndPlayerId("tetris", "alice"))
                .thenReturn(Optional.empty());

        leaderboardService.applyScore(event, "evt-1");

        verify(scoreRepository).save(argThat(ps ->
                ps.getPlayerId().equals("alice") &&
                ps.getTotalScore() == 100
        ));
        verify(processedEventRepository).save(any());
    }

}