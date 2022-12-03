package vn.noron.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.kafka")
public class KafkaConfigCommon {
    private String bootstrapServers;
    private String keySerializerClassConfig;
    private String valueSerializerClassConfig;
    private String acksConfig;
}
