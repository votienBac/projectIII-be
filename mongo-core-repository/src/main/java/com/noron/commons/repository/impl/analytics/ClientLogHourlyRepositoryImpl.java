package com.noron.commons.repository.impl.analytics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.noron.commons.data.model.analytics.ClientLogHourly;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;

@Repository
public class ClientLogHourlyRepositoryImpl
        extends AbsMongoRepository<ClientLogHourly>
        implements IClientLogHourlyRepository {

    public ClientLogHourlyRepositoryImpl(MongoCollection<Document> clientLogHourlyCollection) {
        super(clientLogHourlyCollection);
    }

    @Override
    public List<ClientLogHourly> getByUserIdFromTime(List<String> userIds, Long fromTime, Long toTime) {
        return execute(and(in(USER_ID, userIds), gte(UPDATED_AT, fromTime), lte(UPDATED_AT, toTime)));
    }

    @Override
    public List<ClientLogHourly> getByClientIdFromTime(List<String> clientIds, Long fromTime, Long toTime) {
        return execute(and(in(CLIENT_ID, clientIds), gte(UPDATED_AT, fromTime), lte(UPDATED_AT, toTime)));
    }

    @Override
    public void addUserId(String clientId, String userId) {
        mongoCollection.updateMany(
                and(eq(CLIENT_ID, clientId), eq(USER_ID, null)),
                Updates.set(USER_ID, userId));
    }

    @Override
    protected String getFieldRangeTime() {
        return UPDATED_AT;
    }
}
