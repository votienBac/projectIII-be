package com.noron.commons.repository.impl.analytics;


import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.noron.commons.data.model.analytics.UserPostFrequency;
import com.noron.commons.data.model.analytics.UserPostFrequencyDaily;
import com.noron.commons.data.model.analytics.UserPostViewTime;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.utils.TimeUtil.getStartTimeBeforeXDay;
import static java.util.Arrays.asList;

@Repository
public class UserPostFrequencyDailyRepositoryImpl extends AbsMongoRepository<UserPostFrequencyDaily>
        implements IUserPostFrequencyDailyRepository {

    public UserPostFrequencyDailyRepositoryImpl(MongoCollection<Document> userPostFrequencyDailyCollection) {
        super(userPostFrequencyDailyCollection);
    }

    @Override
    public void updateOnInsert(List<UserPostFrequencyDaily> dailies) {
        final List<UpdateOneModel<Document>> updateOneModels = dailies.stream()
                .map(t -> new UpdateOneModel<Document>(
                        eq(_ID, t.getId()),
                        combine(
                                inc(NUM_PAGEVEW, t.getNumPageview()),
                                inc(NUM_SCROLL_FEED, t.getNumScrollFeed()),
                                set(USER_ID, t.getUserId()),
                                set(FULL_DATE, t.getFullDate()),
                                set(POST_ID, t.getPostId()),
                                set(UPDATED_AT, t.getUpdatedAt())),
                        new UpdateOptions().upsert(true)))
                .collect(Collectors.toList());
        if (updateOneModels.isEmpty()) return;
        Lists.partition(updateOneModels, 5000).forEach(mongoCollection::bulkWrite);
    }

    @Override
    public List<UserPostFrequencyDaily> getByUserIdsBeforeXDay(List<String> userIds,
                                                               Integer numDayBefore) {
        return execute(and(
                in(USER_ID, userIds),
                gte(UPDATED_AT, getStartTimeBeforeXDay(numDayBefore))));
    }

    @Override
    public List<UserPostFrequencyDaily> getBeforeXDay(List<String> userIds,
                                                      List<String> postIds, Integer numDayBefore) {
        return execute(and(
                in(USER_ID, userIds),
                in(POST_ID, postIds),
                gte(UPDATED_AT, getStartTimeBeforeXDay(numDayBefore))));
    }

    @Override
    public List<UserPostFrequency> collectUserPostFrequency(List<String> userIds,
                                                            Long startTime, Long endTime) {
        return mongoCollection
                .aggregate(asList(
                        match(and(gte(UPDATED_AT, startTime), lte(UPDATED_AT, endTime))),
                        match(in(USER_ID, userIds)),
                        group(new Document("user_id", "$user_id").append("post_id", "$post_id"),
                                Accumulators.sum(NUM_PAGEVEW, "$".concat(NUM_PAGEVEW)),
                                Accumulators.sum(NUM_SCROLL_FEED, "$".concat(NUM_SCROLL_FEED)),
                                first(USER_ID, "$".concat(USER_ID)),
                                Accumulators.max(UPDATED_AT, "$".concat(UPDATED_AT)),
                                first(POST_ID, "$".concat(POST_ID)))))
                .map(document -> new JsonObject(document).mapTo(UserPostFrequency.class))
                .into(new ArrayList<>());
    }

    @Override
    protected String getFieldRangeTime() {
        return UPDATED_AT;
    }

    @Override
    public List<String> getPostIdsOverPeriod(Long fromTime, Long endTime) {
        return mongoCollection
                .aggregate(asList(
                        match(gte(UPDATED_AT, fromTime)),
                        match(lte(UPDATED_AT, endTime)),
                        group("$".concat(POST_ID))))
                .map(document -> document.getString(_ID))
                .into(new ArrayList<>());
    }

    @Override
    public List<UserPostFrequencyDaily> getPostsOverPeriod(List<String> postIds,
                                                           List<String> ownerIds,
                                                           Long fromTime,
                                                           Long endTime) {
        return mongoCollection
                .aggregate(asList(
                        match(gte(UPDATED_AT, fromTime)),
                        match(lte(UPDATED_AT, endTime)),
                        match(in(POST_ID, postIds)),
                        match(in(USER_ID, ownerIds)),
                        project(include(POST_ID, USER_ID, UPDATED_AT))))
                .map(doc -> new JsonObject(doc).mapTo(UserPostFrequencyDaily.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<UserPostViewTime> getUserPostViewTime(List<String> ids) {
        return mongoCollection
                .aggregate(asList(
                        match(in(_ID, ids)),
                        match(gt(NUM_PAGEVEW, 0)),
                        group(new Document("user_id", "$user_id").append("post_id", "$post_id"),
                                first(USER_ID, "$".concat(USER_ID)),
                                first(POST_ID, "$".concat(POST_ID)),
                                sum(NUM_PAGEVEW, "$".concat(NUM_PAGEVEW)),
                                Accumulators.addToSet("dates", "$".concat(UPDATED_AT)))))
                .map(doc -> new JsonObject(doc).mapTo(UserPostViewTime.class))
                .into(new ArrayList<>());
    }
}
