package vn.noron.kafka.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Component
public class ProducerConfigCommon {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KafkaConfigCommon kafkaConfig;
    private Properties properties;

    public ProducerConfigCommon(KafkaConfigCommon kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    @Bean
    public Producer<String, String> producer() {
        logger.info("Kafka Config: {}, {}, {}, {}", kafkaConfig.getBootstrapServers(), kafkaConfig.getKeySerializerClassConfig(),
                kafkaConfig.getValueSerializerClassConfig(), kafkaConfig.getAcksConfig());
        properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, kafkaConfig.getKeySerializerClassConfig());
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfig.getValueSerializerClassConfig());
        properties.put(ACKS_CONFIG, kafkaConfig.getAcksConfig());
        return new KafkaProducer<>(properties);
    }
}
