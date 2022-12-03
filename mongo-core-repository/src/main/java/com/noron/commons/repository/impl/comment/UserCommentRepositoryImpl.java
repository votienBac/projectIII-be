package com.noron.commons.repository.impl.comment;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.noron.commons.data.constant.status.Status;
import com.noron.commons.data.model.SearchRequest;
import com.noron.commons.data.model.comment.Comment;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.ObjectType.ANSWER;
import static com.noron.commons.data.constant.status.Status.ACTIVE;
import static com.noron.commons.data.constant.status.Status.WAITING_POST;
import static com.noron.commons.utils.PostUtils.decodePostId;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

@Repository
public class UserCommentRepositoryImpl extends AbsMongoRepository<Comment> implements ICommentRepository {

    public UserCommentRepositoryImpl(MongoCollection<Document> commentCollection) {
        super(commentCollection);
    }

    @Override
    protected Bson filterActive() {
        return and(
                in(VERIFIED, ACTIVE, WAITING_POST),
                ne(POST_DELETED, true),
                nin(TOPIC_IDS, "98355434850628658", "98638123138812592"));
    }

    @Override
    public List<Comment> getByPostIds(Collection<String> postIds) {
        return execute(in(POST_ID, postIds), filterActive());
    }

    @Override
    public List<Comment> getByType(String type) {
        return execute(eq(TYPE, type), filterActive());
    }

    @Override
    public List<Comment> getByParentIds(Collection<String> parentIds) {
        return execute(in(PARENT_ID, parentIds), filterActive());
    }

    @Override
    public List<Comment> getAnswersOfPosts(Collection<String> postIds) {
        return execute(in(POST_ID, postIds), eq(TYPE, ANSWER), filterActive());
    }

    @Override
    public Long countContentOfUser(String userId, String type) {
        return count(eq(OWNER_ID, userId), filterActive(), eq(TYPE, type));
    }

    @Override
    public Map<String, Long> countContentOfUsers(List<String> userIds, String type) {
        final HashMap<String, Long> result = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(in(OWNER_ID, userIds)),
                        match(eq(TYPE, type)),
                        match(filterActive()),
                        group("$" + OWNER_ID, Accumulators.sum("total", 1))
                ))
                .iterator()
                .forEachRemaining(document -> {
                    final String userId = document.get(_ID).toString();
                    final Long total = createLong(document.get("total").toString());
                    result.put(userId, total);
                });
        return result;
    }

    @Override
    public List<Comment> getByPostId(String postId) {
        return execute(and(eq(POST_ID, postId), eq(VERIFIED, Status.ACTIVE)));
    }

    @Override
    public List<Comment> getByPostIdPostType(String postId, String... postTypes) {
        return execute(and(
                eq(POST_ID, postId),
                in(POST_TYPE, postTypes),
                eq(VERIFIED, Status.ACTIVE)));
    }

    @Override
    public List<Comment> getByRangeTime(Long fromTime, Long toTime, String type) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(TYPE, type),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(Comment.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Comment> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(OWNER_ID, userIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(Comment.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Comment> getCommentOfSeeder(List<String> seederIds) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(OWNER_ID, seederIds),
                        eq(VERIFIED, Status.ACTIVE)))
                .map(document -> new JsonObject(document).mapTo(Comment.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Comment> getByUserId(String userId) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(OWNER_ID, userId)))
                .map(document -> new JsonObject(document).mapTo(Comment.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<String> getCommentIds(String type) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(VERIFIED, Status.ACTIVE),
                        eq(TYPE, type)))
                .map(document -> document.get(_ID).toString())
                .into(new ArrayList<>());
    }

    @Override
    public void incNumVote(List<String> commentIds, Integer numVote) {
        mongoCollection.updateMany(
                in(_ID, commentIds),
                inc(NUM_UP_VOTE, numVote));
    }

    protected Bson buildSearchQueries(SearchRequest request) {
        final String keyword = request.getKeyword();
        if (isNotEmpty(keyword)) {
            final String postId = decodePostId(keyword);
            if (postId != null) {
                return Filters.or(Filters.regex(CONTENT, ".*" + keyword + ".*"), Filters.eq(POST_ID, postId));
            }
            return Filters.regex(CONTENT, ".*" + keyword + ".*");
        }
        return super.buildSearchQueries(request);
    }

}
