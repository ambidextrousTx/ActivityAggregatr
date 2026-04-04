package com.leaderboard.service;

import com.leaderboard.common.ScoreEvent;
import com.leaderboard.model.PlayerScore;
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
import static org.mockito.Mockito.*;

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

    @Test
    void duplicateEvent_isSkipped() {
        var event = new ScoreEvent("alice", "tetris", 100, System.currentTimeMillis());
        when(processedEventRepository.existsById("evt-1")).thenReturn(true);

        leaderboardService.applyScore(event, "evt-1");

        verifyNoInteractions(scoreRepository);
    }

    @Test
    void existingPlayer_scoreIsAccumulated() {
        var event = new ScoreEvent("alice", "tetris", 50, System.currentTimeMillis());
        var existingRow = new PlayerScore("tetris", "alice", 100);
        when(processedEventRepository.existsById("evt-2")).thenReturn(false);
        when(scoreRepository.findByGameAndPlayerId("tetris", "alice"))
                .thenReturn(Optional.of(existingRow));

        leaderboardService.applyScore(event, "evt-2");

        verify(scoreRepository, never()).save(any());  // no new row created
        assert existingRow.getTotalScore() == 150;     // mutated in place
    }

}