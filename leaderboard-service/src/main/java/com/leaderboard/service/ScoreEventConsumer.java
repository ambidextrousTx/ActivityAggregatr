package com.leaderboard.service;

import com.leaderboard.common.ScoreEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class ScoreEventConsumer {
    private final LeaderboardService service;

    public ScoreEventConsumer(LeaderboardService service) {
        this.service = service;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            dltTopicSuffix = ".DLT",
            exclude = {IllegalArgumentException.class}
    )
    @KafkaListener(topics = "score.events", groupId = "leaderboard-service")
    public void consume(ConsumerRecord<String, ScoreEvent> record) {
        // ConsumerRecord gives us access to the raw Kafka metadata —
        // key, partition, offset, timestamp — useful for debugging
        String eventId = record.topic() + "-" + record.partition() + "-" + record.offset();
        System.out.println("Consuming: " + eventId);
        service.applyScore(record.value(), eventId);
    }

    @DltHandler
    public void handleDlt(ConsumerRecord<String, ?> record) {
        System.err.println("Message landed in DLT: topic=" + record.topic()
                           + " offset=" + record.offset()
                           + " value=" + record.value());
    }
}
