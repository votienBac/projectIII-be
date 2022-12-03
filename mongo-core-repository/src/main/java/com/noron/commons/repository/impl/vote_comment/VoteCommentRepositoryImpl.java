package com.noron.commons.repository.impl.vote_comment;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.vote.VoteComment;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

@Repository
public class VoteCommentRepositoryImpl extends AbsMongoRepository<VoteComment> implements IVoteCommentRepository {
    public VoteCommentRepositoryImpl(MongoCollection<Document> voteCommentCollection) {
        super(voteCommentCollection);
    }

    @Override
    public List<VoteComment> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        or(in(OWNER_ID, userIds), in(OWNER_COMMENT, userIds)),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(VoteComment.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<VoteComment> getByRangeTimeOfCommentIds(Long fromTime, Long toTime, List<String> commentIds) {
        return mongoCollection
                .find(and(
                        in(COMMENT_ID, commentIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(VoteComment.class))
                .into(new ArrayList<>());
    }

    @Override
    public Map<String, Long> aggVoteComment() {
        final HashMap<String, Long> voteCommentMap = new HashMap<>();
        mongoCollection
                .aggregate(asList(group("$" + COMMENT_ID, sum(VALUE, "$" + VALUE))))
                .iterator()
                .forEachRemaining(document -> {
                    if (document.containsKey(_ID) && document.containsKey(VALUE))
                        voteCommentMap.put(document.get(_ID).toString(), createLong(document.get(VALUE).toString()));
                });
        return voteCommentMap;
    }
}
