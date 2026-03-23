package com.score.service;

import com.leaderboard.common.ScoreEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScoreProducer {
    private static final String TOPIC = "score.events";
    private final KafkaTemplate<String, ScoreEvent> kafkaTemplate;

    public ScoreProducer(KafkaTemplate<String, ScoreEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishScore(ScoreEvent event) {
        kafkaTemplate.send(TOPIC, event.playerId(), event);
    }
}