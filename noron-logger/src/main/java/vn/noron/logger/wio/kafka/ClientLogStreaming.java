package vn.noron.logger.wio.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vn.noron.core.json.JsonObject;
import vn.noron.data.log.NoronLog;
import vn.noron.logger.service.ClientLogConsumer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClientLogStreaming {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ClientLogConsumer clientLogConsumer;

    public ClientLogStreaming(ClientLogConsumer clientLogConsumer) {
        this.clientLogConsumer = clientLogConsumer;
    }


    @KafkaListener(topics = "${spring.kafka.topics.noron-client-log}",
            groupId = "${spring.kafka.group.noron-client-log-consumer}",
            containerFactory = "customKafkaListenerContainerFactory")
    public void consume(List<String> message) {
        final List<NoronLog> noronLogs = message.stream()
                .map(this::convertToNoronLog)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        clientLogConsumer.handle(noronLogs);

    }

    private NoronLog convertToNoronLog(String s) {
        try {
            return new JsonObject(s).mapTo(NoronLog.class);
        } catch (Exception exception) {
            return null;
        }
    }
}