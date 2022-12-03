package com.noron.commons.repository.impl.feed.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.noron.commons.data.model.feed.FeedContent;
import com.noron.commons.repository.AbsMongoRepository;
import com.noron.commons.repository.impl.feed.IBaseFeedContentRepository;
import org.bson.Document;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static java.util.Arrays.asList;

public abstract class AbsFeedContentRepository<T> extends AbsMongoRepository<FeedContent> implements IBaseFeedContentRepository {

    public AbsFeedContentRepository(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);
    }

    @Override
    public void deletedOldDataOfUserByTime(String userId, Long lastTime) {
        mongoCollection.deleteMany(and(eq(USER_ID, userId), lt(UPDATED_AT, lastTime)));
    }

    @Override
    public void deletedOldDataOfUserIsByTime(List<String> userIds, Long lastTime) {
        mongoCollection.deleteMany(and(in(USER_ID, userIds), lt(UPDATED_AT, lastTime)));
    }

    @Override
    public void deletedOldDataByTime(Long lastTime) {
        mongoCollection.deleteMany(lt(UPDATED_AT, lastTime));
    }

    @Override
    public List<String> incOrderItemsOfUser(String userId, List<String> postIds, Integer incOrder) {
        mongoCollection.updateMany(
                and(eq(USER_ID, userId), in(POST_ID, postIds)),
                inc(ORDER, incOrder));
        return mongoCollection.find(and(eq(USER_ID, userId), in(POST_ID, postIds)))
                .projection(Projections.include("key"))
                .map(document -> document.getString("key"))
                .into(new ArrayList<>());
    }
}
