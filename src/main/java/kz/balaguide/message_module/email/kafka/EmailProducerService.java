package kz.balaguide.message_module.email.kafka;

import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.entities.Receipt;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class EmailProducerService {

    private final KafkaTemplate<String, Receipt> kafkaTemplate;

    /**
     * Sends a {@link Receipt} message to the "receipt" Kafka topic.
     *
     * @param receipt the {@link Receipt} object to be sent to Kafka
     */
    public void sendReceiptToTopic(Receipt receipt) {
        kafkaTemplate.executeInTransaction(kafkaTemplate -> {
            kafkaTemplate.send("receipt", String.valueOf(receipt.getId()), receipt);
            return new Object();
        });
    }
}
