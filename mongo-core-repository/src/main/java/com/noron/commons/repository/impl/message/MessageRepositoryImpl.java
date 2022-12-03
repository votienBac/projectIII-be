package com.noron.commons.repository.impl.message;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.constant.status.Status;
import com.noron.commons.data.model.Message;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.utils.ObjTransformUtils.toDocumentInsert;

@Repository
public class MessageRepositoryImpl extends AbsMongoRepository<Message> implements IMessageRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired private KafkaSendMessageService kafkaService;

    public MessageRepositoryImpl(MongoCollection<Document> messagesCollection) {
        super(messagesCollection);
    }

    @Override
    public List<Message> getMessagesFromTime(long fromTime) {
        return mongoCollection.find(and(eq(STATUS, Status.ACTIVE), gte(CREATION_TIME, fromTime)))
                .map(document -> new JsonObject(document).mapTo(Message.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Message> getMessages(long fromTime, long toTime) {
        return mongoCollection
                .find(and(
                        eq(STATUS, Status.ACTIVE),
                        lte(CREATION_TIME, toTime),
                        gte(CREATION_TIME, fromTime)))
                .map(document -> new JsonObject(document).mapTo(Message.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Message> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(SENDER_ID, userIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(Message.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<String> getDialogIds() {
        return mongoCollection.aggregate(
                Collections.singletonList(group("$" + DIALOG_ID)))
                .map(document -> new JsonObject(document).mapTo(Message.class))
                .map(Message::getDialogId)
                .into(new ArrayList<>());
    }

    @Override
    public Message saveMessage(Message message) {
        try {
            mongoCollection.insertOne(toDocumentInsert(message));
            // todo
//            kafkaService.sendNewMessageLog(message);
            return message;
        } catch (Exception e) {
            logger.error("Can not save message {}, {}", message, e.getCause());
            return null;
        }
    }
}
