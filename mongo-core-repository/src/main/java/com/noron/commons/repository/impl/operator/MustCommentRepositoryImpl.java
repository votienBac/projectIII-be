package com.noron.commons.repository.impl.operator;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.noron.commons.data.model.comment.Comment;
import com.noron.commons.data.model.operator.MustComment;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.ObjectType.ANSWER;
import static com.noron.commons.data.constant.ObjectType.BLOG;
import static com.noron.commons.data.constant.status.Status.*;
import static com.noron.commons.utils.ObjTransformUtils.toDocUpdate;
import static com.noron.commons.utils.TimeUtil.getCurrentDateEndDay;
import static com.noron.commons.utils.TimeUtil.getStartTimeOfDay;

@Repository
public class MustCommentRepositoryImpl extends AbsMongoRepository<MustComment> implements IMustCommentRepository {

    public MustCommentRepositoryImpl(MongoCollection<Document> mustCommentCollection) {
        super(mustCommentCollection);
    }

    @Override
    protected Bson filterActive() {
        return nin(STATUS, -1);
    }

    @Override
    public void deleteNotInRangTime(Long startTime, Long endTime, Collection<String> ids) {
        mongoCollection.deleteMany(
                and(
                        gte(getFieldRangeTime(), startTime),
                        lte(getFieldRangeTime(), endTime),
                        nin(_ID, ids)
                ));
    }

    @Override
    public void deleteNotAcceptType() {
        mongoCollection.deleteMany(nin(TYPE, ANSWER, BLOG));
    }

    @Override
    public void deleteByPostIds(List<String> postIds, String type) {
        mongoCollection.deleteMany(and(in(POST_ID, postIds), eq(TYPE, type)));
    }

    @Override
    public void saveMany(List<MustComment> mustComments, Function<MustComment, Boolean> upsert) {
        saveMany(mustComments, mustComment -> eq(_ID, mustComment.getId()), upsert);
    }

    @Override
    public void deleteByPostId(String postId) {
        mongoCollection.deleteMany(and(eq(POST_ID, postId)));
    }

    @Override
    public void deleteByCommentId(Comment comment) {
        mongoCollection.deleteMany(and(eq(COMMENT_ID, comment.getId())));
        decreasingNumComment(comment.getPostId(), comment.getParentId());
    }

    private void decreasingNumComment(String postId, String parentId) {
        mongoCollection.updateOne(
                and(
                        eq(POST_ID, postId),
                        eq(COMMENT_ID, parentId)),
                inc(NUM_COMMENT, -1));
        MustComment mustComment = mongoCollection
                .find(
                        and(
                                eq(POST_ID, postId),
                                eq(COMMENT_ID, parentId)))
                .map(document -> new JsonObject(document).mapTo(MustComment.class))
                .first();
        mongoCollection.updateOne(
                and(
                        eq(POST_ID, postId),
                        ne(DONE_AT, null),
                        lt(NUM_COMMENT, mustComment.getNumMustComment())),
                combine(
                        set(DONE_AT, null),
                        set(STATUS, MUST_COMMENT_NOT_DONE))
        );
    }

    @Override
    public Long countMustCommentDoneThisDay(List<String> topicIds, List<String> blackListUserIds) {
        final Bson queries = and(
                in(TOPIC_IDS, topicIds),
                nin(OWNER_ID, blackListUserIds),
                eq(STATUS, OPERATOR_DONE),
                gte(DONE_AT, getStartTimeOfDay()),
                lte(DONE_AT, getCurrentDateEndDay()));
        return mongoCollection.countDocuments(queries);
    }

    @Override
    public Long countContentNotDone(List<String> topicIds, List<String> blackListUserIds) {
        final Bson queries = and(
                in(TOPIC_IDS, topicIds),
                nin(OWNER_ID, blackListUserIds),
                eq(STATUS, OPERATOR_NOT_DONE));
        return mongoCollection.countDocuments(queries);
    }

    @Override
    public List<MustComment> getMustComments(List<String> blackListUserIds) {
        final Bson queries = and(
                nin(OWNER_ID, blackListUserIds),
                eq(STATUS, OPERATOR_NOT_DONE),
                eq(DONE_AT, null));
        return mongoCollection
                .find(queries)
                .map(document -> new JsonObject(document).mapTo(MustComment.class))
                .into(new ArrayList<>());
    }

    @Override
    public void incNumMustComment(String commentId, String postId) {
        final Bson queries = and(
                eq(COMMENT_ID, commentId),
                eq(POST_ID, postId)
        );
        // tang numcoment
        // check num comment with nummust commnet.
        // de xet done, ...
        // luc ma commnt add vao.
        // check lai num must comment.
    }

    @Override
    public List<MustComment> getMustComments(List<String> blackListUserIds, Long from, Long to) {
        final Bson queries = or(
                and(nin(OWNER_ID, blackListUserIds), eq(STATUS, OPERATOR_NOT_DONE), eq(DONE_AT, null)),
                and(gte(CREATION_TIME, from), lte(CREATION_TIME, to)),
                and(gte(UPDATED_AT, to), lte(UPDATED_AT, from)));
        return mongoCollection
                .find(queries)
                .map(document -> new JsonObject(document).mapTo(MustComment.class))
                .into(new ArrayList<>());
    }

    @Override
    public MustComment update(Object id, MustComment mustComment) {
        List<Bson> bson = toDocUpdate(mustComment);
        bson.add(set(DONE_AT, mustComment.getDoneAt()));
        mongoCollection.updateOne(Filters.eq(_ID, id), combine(bson));
        return mustComment;
    }

    @Override
    public List<MustComment> getByOperatorIds(String operatorId) {
        return mongoCollection
                .find(and(
                        eq(OPERATOR_IDS, operatorId)))
                .map(document -> new JsonObject(document).mapTo(MustComment.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<MustComment> getByOperatorId(String operatorId) {
        return mongoCollection
                .find(and(
                        eq(OPERATOR_ID, operatorId)))
                .map(document -> new JsonObject(document).mapTo(MustComment.class))
                .into(new ArrayList<>());
    }
}