package com.leaderboard.service;

import com.leaderboard.common.ScoreEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ScoreEventConsumer {
    private final LeaderboardService service;

    public ScoreEventConsumer(LeaderboardService service) {
        this.service = service;
    }

    @KafkaListener(topics = "score.events", groupId = "leaderboard-service")
    public void consume(ConsumerRecord<String, ScoreEvent> record) {
        // ConsumerRecord gives us access to the raw Kafka metadata —
        // key, partition, offset, timestamp — useful for debugging
        ScoreEvent scoreEvent = record.value();
        String eventId = record.topic() + "-" + record.partition() + "-" + record.offset();
        System.out.println("Consuming: " + eventId);
        service.applyScore(scoreEvent, eventId);
    }
}
