package com.leaderboard.leaderboard;

import com.leaderboard.common.ScoreEvent;
import com.leaderboard.model.PlayerScoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = {"score.events"},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
@DirtiesContext
class ScoreEventConsumerIntegrationTest {

    @Autowired
    KafkaTemplate<String, ScoreEvent> kafkaTemplate;

    @Autowired
    PlayerScoreRepository repository;

    @Test
    void publishedScore_isConsumedAndPersisted() {
        var event = new ScoreEvent("bob", "chess", 200, System.currentTimeMillis());

        kafkaTemplate.send("score.events", "bob", event);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            var scores = repository.findByGame("chess");
            assertThat(scores).hasSize(1);
            assertThat(scores.getFirst().getTotalScore()).isEqualTo(200);
        });
    }
}