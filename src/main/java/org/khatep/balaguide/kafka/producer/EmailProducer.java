package org.khatep.balaguide.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.models.entities.Receipt;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final KafkaTemplate<String, Receipt> kafkaTemplate;

    /**
     * Sends a {@link Receipt} message to the "receipt" Kafka topic.
     *
     * @param receipt the {@link Receipt} object to be sent to Kafka
     */
    public void sendMessage(Receipt receipt) {
        kafkaTemplate.send("receipt", receipt);
    }
}
