package com.noron.commons.repository.impl.feed.impl;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.feed.UserPostScore;
import com.noron.commons.repository.AbsMongoRepository;
import com.noron.commons.repository.impl.feed.IFeedUserPostRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;
import static com.noron.commons.data.constant.GenericFieldConstant.*;

@Repository
public class FeedUserPostRepositoryImpl extends AbsMongoRepository<UserPostScore> implements IFeedUserPostRepository {

    public FeedUserPostRepositoryImpl(MongoCollection<Document> feedUserPostCollection) {
        super(feedUserPostCollection);
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
    public void incOrderItemsOfUser(String userId, List<String> postIds, Integer incOrder) {
        mongoCollection.updateMany(
                and(eq(USER_ID, userId), in(POST_ID, postIds)),
                inc(ORDER, incOrder));
    }
}
