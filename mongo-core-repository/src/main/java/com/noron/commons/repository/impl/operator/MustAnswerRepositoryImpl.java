package com.noron.commons.repository.impl.operator;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.noron.commons.data.constant.ObjectType;
import com.noron.commons.data.model.operator.MustAnswer;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.status.Status.*;
import static com.noron.commons.utils.ObjTransformUtils.toDocUpdate;

@Repository
public class MustAnswerRepositoryImpl extends AbsMongoRepository<MustAnswer> implements IMustAnswerRepository {

    public MustAnswerRepositoryImpl(MongoCollection<Document> mustAnswerCollection) {
        super(mustAnswerCollection);
    }

    @Override
    protected Bson filterActive() {
        return nin(STATUS, MUST_ANSWER_IGNORE);
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
    public void deleteBlog() {
        mongoCollection.deleteMany(ne(POST_TYPE, ObjectType.QUESTION));
    }

    @Override
    public void saveMany(List<MustAnswer> mustAnswers, Function<MustAnswer, Boolean> upsert) {
        saveMany(mustAnswers, p -> eq(_ID, p.getId()), upsert);
    }

    @Override
    public MustAnswer update(MustAnswer mustAnswer) {
        mongoCollection.updateOne(
                and(eq(_ID, mustAnswer.getId()), eq(STATUS, MUST_ANSWER_NOT_DONE)),
                combine(toDocUpdate(mustAnswer, _ID)));
        mongoCollection.updateOne(
                and(eq(_ID, mustAnswer.getId()), ne(STATUS, MUST_ANSWER_NOT_DONE)),
                combine(toDocUpdate(mustAnswer, _ID, DONE_AT, IGNORE_BY)));
        return mustAnswer;
    }

    @Override
    public void deleteByPostId(String postId) {
        mongoCollection.deleteMany(and(eq(POST_ID, postId)));
    }

    @Override
    public List<MustAnswer> getMustAnswers(List<String> blackListUserIds) {
        return mongoCollection.find(and(in(OWNER_ID, blackListUserIds),
                        eq(STATUS, OPERATOR_NOT_DONE),
                        eq(DONE_AT, null)))
                .map(document -> new JsonObject(document).mapTo(MustAnswer.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<MustAnswer> getByOperatorIds(String operatorId) {
        return mongoCollection
                .find(and(
                        eq(OPERATOR_IDS, operatorId)))
                .map(document -> new JsonObject(document).mapTo(MustAnswer.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<MustAnswer> getByOperatorId(String operatorId) {
        return mongoCollection
                .find(and(
                        eq(OPERATOR_ID, operatorId)))
                .map(document -> new JsonObject(document).mapTo(MustAnswer.class))
                .into(new ArrayList<>());
    }
}