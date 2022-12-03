package com.noron.commons.repository.impl.message;


import com.noron.commons.data.model.Message;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IMessageRepository extends IMongoRepository<Message> {
    List<Message> getMessagesFromTime(long fromTime);

    List<Message> getMessages(long fromTime, long toTime);

    List<Message> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);

    List<String> getDialogIds();

    Message saveMessage(Message message);

}
