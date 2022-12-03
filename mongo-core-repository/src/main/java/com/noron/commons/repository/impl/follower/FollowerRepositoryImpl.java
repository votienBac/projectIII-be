package com.noron.commons.repository.impl.follower;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.noron.commons.data.model.follow.Follower;
import com.noron.commons.repository.AbsMongoRepository;
import com.noron.commons.utils.ObjTransformUtils;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.mongodb.client.model.Filters.in;

@Repository
public class FollowerRepositoryImpl extends AbsMongoRepository<Follower> implements IFollowerRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FollowerRepositoryImpl(MongoCollection<Document> followerCollection) {
        super(followerCollection);
    }

    public Map<String, Integer> countFollowerMap(List<String> userIds) {
        HashMap<String, Integer> userNumFollowerMap = new HashMap();
        this.mongoCollection.aggregate(Arrays.asList(Aggregates.match(in("user_id", userIds)), Aggregates.group("$user_id", new BsonField[]{Accumulators.sum("num_follower", 1)}))).iterator().forEachRemaining((document) -> {
            if (document.containsKey("num_follower")) {
                userNumFollowerMap.put(document.getString("_id"), Integer.parseInt(document.get("num_follower").toString()));
            }

        });
        return userNumFollowerMap;
    }

    public String followUser(Follower follower) {
        if (this.checkFollowedStatus(follower.getFollowerId(), follower.getUserId())) {
            return "followed";
        } else {
            this.mongoCollection.insertOne(ObjTransformUtils.toDocumentInsert(follower));
            return "success";
        }
    }

    public Boolean checkFollowedStatus(String loggedUserId, String userId) {
        return this.mongoCollection.countDocuments(Filters.and(new Bson[]{Filters.eq("user_id", userId), Filters.eq("follower_id", loggedUserId)})) > 0L;
    }

    @Override
    public List<Follower> getFollowers(List<String> userIds) {
        return mongoCollection
                .find(in("follower_id", userIds))
                .map(document -> new JsonObject(document).mapTo(Follower.class))
                .into(new ArrayList<>());
    }
}