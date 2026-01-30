package kz.balaguide.common_module.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import kz.balaguide.common_module.core.entities.Receipt;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Bean
    public NewTopic receiptTopic() {
        return new NewTopic("receipt", 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, Receipt> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Для "exactly-once"
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // включаем идемпотентность
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");               // ждём подтверждения от всех реплик
        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE); // бесконечные повторные попытки
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5); // безопасное значение для EOS

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Receipt> kafkaTemplate() {
        KafkaTemplate<String, Receipt> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setTransactionIdPrefix("receipt-tx-"); // префикс для транзакций
        return kafkaTemplate;
    }

}
