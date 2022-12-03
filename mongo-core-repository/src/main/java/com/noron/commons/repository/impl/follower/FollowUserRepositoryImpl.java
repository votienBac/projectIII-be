package com.noron.commons.repository.impl.follower;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.noron.commons.data.model.follow.FollowUser;
import com.noron.commons.repository.AbsMongoRepository;
import com.noron.commons.utils.TimeUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class FollowUserRepositoryImpl extends AbsMongoRepository<FollowUser> implements IFollowUserRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FollowUserRepositoryImpl(MongoCollection<Document> followUserCollection) {
        super(followUserCollection);
    }

    public String followUser(String loginUserId, String userId) {
        if (this.checkFollowedStatus(loginUserId, userId)) {
            return "followed";
        } else {
            long var10000 = TimeUtil.getTimestamp().getTime();
            String userFollerWithTime = var10000 + ":" + userId;
            var10000 = TimeUtil.getTimestamp().getTime();
            String userFollwingWithTime = var10000 + ":" + loginUserId;
            this.mongoCollection.updateOne(Filters.eq("_id", loginUserId), Updates.addToSet("followed_user", userFollerWithTime), (new UpdateOptions()).upsert(true));
            this.mongoCollection.updateOne(Filters.eq("_id", userId), Updates.addToSet("follower", userFollwingWithTime), (new UpdateOptions()).upsert(true));
            return "success";
        }
    }

    public Boolean checkFollowedStatus(String loggedUserId, String userId) {
        return this.mongoCollection.countDocuments(Filters.and(new Bson[]{Filters.eq("_id", loggedUserId), Filters.regex("followed_user", ":" + userId)})) > 0L;
    }
}