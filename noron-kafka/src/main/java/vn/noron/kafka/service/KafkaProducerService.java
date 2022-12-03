package vn.noron.kafka.service;

import io.reactivex.rxjava3.core.Single;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import vn.noron.core.log.Logger;
import vn.noron.kafka.config.KafkaTopicModel;

import static vn.noron.core.template.RxTemplate.rxSchedulerNewThread;
import static vn.noron.core.template.RxTemplate.rxSchedulerNewThreadSubscribe;

@Service
public class KafkaProducerService {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final KafkaTopicModel kafkaTopic;
    private final Producer<String, String> producer;

    public KafkaProducerService(KafkaTopicModel kafkaTopic,
                                Producer<String, String> producer) {
        this.kafkaTopic = kafkaTopic;
        this.producer = producer;
    }

    public Single<String> sendNoronActionLog(String key, String value) {
        return sendMessage(kafkaTopic.getNoronActionLog(), key, value);
    }

    public Single<String> sendMessage(String topic, String key, String value) {
        return rxSchedulerNewThread(() -> {
            ProducerRecord<String, String> rec = new ProducerRecord<>(topic, key, value);
            producer.send(rec);
            producer.flush();
            return value;
        });
    }

    public void sendMessageBlocking(String topic, String key, String value) {
        rxSchedulerNewThreadSubscribe(() -> {
            ProducerRecord<String, String> rec = new ProducerRecord<>(topic, key, value);
            producer.send(rec);
            producer.flush();
            return value;
        });
    }
}
