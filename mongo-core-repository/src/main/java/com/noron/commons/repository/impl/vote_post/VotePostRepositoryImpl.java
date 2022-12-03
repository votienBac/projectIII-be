package com.noron.commons.repository.impl.vote_post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.vote.VotePost;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

@Repository
public class VotePostRepositoryImpl extends AbsMongoRepository<VotePost> implements IVotePostRepository {
    public VotePostRepositoryImpl(MongoCollection<Document> votePostCollection) {
        super(votePostCollection);
    }

    @Override
    public List<VotePost> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        or(in(OWNER_ID, userIds), in(OWNER_POST, userIds)),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(VotePost.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<VotePost> getByRangeTimeOfPosts(Long fromTime, Long toTime, List<String> postIds) {
        return mongoCollection
                .find(and(
                        in(POST_ID, postIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(VotePost.class))
                .into(new ArrayList<>());
    }

    @Override
    public VotePost getByPostOwnerId(String postId, String ownerId) {
        return Optional
                .ofNullable(mongoCollection
                        .find(and(eq(POST_ID, postId), eq(POST_ID, postId)))
                        .map(document -> new JsonObject(document).mapTo(tClazz))
                        .first())
                .orElse(null);
    }

    @Override
    public Map<String, Long> aggVotePost() {
        final Map<String, Long> votePostMap = new HashMap<>();
        mongoCollection
                .aggregate(asList(group("$" + POST_ID, sum(VALUE, "$" + VALUE))))
                .iterator()
                .forEachRemaining(document -> {
                    if (document.containsKey(_ID) && document.containsKey(VALUE))
                        votePostMap.put(document.get(_ID).toString(), createLong(document.get(VALUE).toString()));
                });
        return votePostMap;
    }
}