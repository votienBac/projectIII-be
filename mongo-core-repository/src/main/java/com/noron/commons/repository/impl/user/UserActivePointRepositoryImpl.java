package com.noron.commons.repository.impl.user;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.user.UserActivePoint;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.TIME;
import static com.noron.commons.data.constant.GenericFieldConstant.USER_ID;

@Repository
public class UserActivePointRepositoryImpl extends AbsMongoRepository<UserActivePoint> implements IUserActivePointRepository {
    public UserActivePointRepositoryImpl(MongoCollection<Document> userActivePointCollection) {
        super(userActivePointCollection);
    }

    @Override
    public List<UserActivePoint> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        in(USER_ID, userIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>());
    }

    @Override
    protected String getFieldRangeTime() {
        return TIME;
    }
}
