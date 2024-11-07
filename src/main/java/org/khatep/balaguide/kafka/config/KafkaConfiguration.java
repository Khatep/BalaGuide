package org.khatep.balaguide.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.khatep.balaguide.models.entities.Receipt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    /**
     * Creates a new Kafka topic named "receipt" with a single partition and a replication factor of 1.
     *
     * <p>This topic is used for publishing receipt-related messages.
     *
     * @return a {@link NewTopic} object representing the Kafka topic "receipt"
     */
    @Bean
    public NewTopic receiptTopic() {
        return new NewTopic("receipt", 1, (short) 1);
    }

    /**
     * Configures and provides a {@link ProducerFactory} for producing Kafka messages
     * with keys as {@link String} and values as {@link Receipt}.
     *
     * <p>The configuration properties specify the Kafka server address, the key serializer,
     * and the JSON serializer for the message values.
     *
     * @return a {@link ProducerFactory} configured for sending receipt messages to Kafka
     */
    @Bean
    public ProducerFactory<String, Receipt> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Provides a {@link KafkaTemplate} for sending messages to Kafka, using the configured producer factory.
     *
     * <p>This template is used to send receipt-related messages to the "receipt" topic.
     *
     * @return a {@link KafkaTemplate} for publishing receipt messages
     */
    @Bean
    public KafkaTemplate<String, Receipt> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
