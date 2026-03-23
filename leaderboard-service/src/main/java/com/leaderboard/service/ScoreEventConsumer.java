package com.leaderboard.service;

import com.leaderboard.model.LeaderboardStore;
import com.leaderboard.common.ScoreEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ScoreEventConsumer {
    private final LeaderboardStore store;

    public ScoreEventConsumer(LeaderboardStore store) {
        this.store = store;
    }

    @KafkaListener(topics = "score.events", groupId = "leaderboard-service")
    public void consume(ConsumerRecord<String, ScoreEvent> record) {
        // ConsumerRecord gives us access to the raw Kafka metadata —
        // key, partition, offset, timestamp — useful for debugging
        ScoreEvent scoreEvent = record.value();
        System.out.println("Consumed: " + scoreEvent);
        store.addScore(scoreEvent.game(), scoreEvent.playerId(), scoreEvent.score());
    }
}
