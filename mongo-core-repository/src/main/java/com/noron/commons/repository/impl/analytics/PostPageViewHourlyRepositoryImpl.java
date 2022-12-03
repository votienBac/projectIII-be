package com.noron.commons.repository.impl.analytics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.noron.commons.data.model.analytics.PostPageViewHourly;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.google.common.collect.Lists.partition;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

@Repository
public class PostPageViewHourlyRepositoryImpl
        extends AbsMongoRepository<PostPageViewHourly>
        implements IPostPageViewHourlyRepository {

    public PostPageViewHourlyRepositoryImpl(MongoCollection<Document> postPageViewHourlyCollection) {
        super(postPageViewHourlyCollection);
    }

    @Override
    public void updateOnInsert(List<PostPageViewHourly> hourlies) {
        final List<UpdateOneModel<Document>> updateOneModels = hourlies.stream()
                .map(hourly -> new UpdateOneModel<Document>(
                        eq(_ID, hourly.getId()),
                        combine(
                                set(POST_ID, hourly.getPostId()),
                                set(SESSION_ID, hourly.getSessionId()),
                                set(SERIES_ID, hourly.getSeriesId()),
                                set(TOPIC_IDS, hourly.getTopicIds()),
                                set(UPDATED_AT, hourly.getUpdatedAt()),
                                inc(NUM_VIEW, hourly.getNumView()),
                                pushEach(CLIENT_IDS, hourly.getClientIds())),
                        new UpdateOptions().upsert(true)))
                .collect(toList());
        if (updateOneModels.isEmpty()) return;
        partition(updateOneModels, 1000).forEach(mongoCollection::bulkWrite);
    }

    @Override
    protected String getFieldRangeTime() {
        return UPDATED_AT;
    }

    @Override
    public List<PostPageViewHourly> getBySessionId(String sessionId) {
        return mongoCollection.find(eq(SESSION_ID, sessionId))
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>());
    }

    @Override
    public Map<String, Long> countNumViewFromTime(Long fromTime, Long toTime) {
        final Map<String, Long> postViewMap = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(gte(UPDATED_AT, fromTime)),
                        match(lte(UPDATED_AT, toTime)),
                        group("$" + POST_ID, sum(NUM_VIEW, "$" + NUM_VIEW))))
                .iterator()
                .forEachRemaining(document -> postViewMap.put(
                        document.get(_ID).toString(),
                        createLong(document.get(NUM_VIEW).toString())));
        return postViewMap;
    }

    @Override
    public Map<String, Long> countNumView(List<String> postIds) {
        final Map<String, Long> postViewMap = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(in(POST_ID, postIds)),
                        group("$" + POST_ID, sum(NUM_VIEW, "$" + NUM_VIEW))))
                .iterator()
                .forEachRemaining(document -> postViewMap.put(
                        document.get(_ID).toString(),
                        createLong(document.get(NUM_VIEW).toString())));
        return postViewMap;
    }

    @Override
    public Map<String, Long> countNumViewPostByRangeTime(Long fromTime, Long toTime, List<String> postIds) {
        final Map<String, Long> postViewMap = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(gte(UPDATED_AT, fromTime)),
                        match(lte(UPDATED_AT, toTime)),
                        match(in(POST_ID, postIds)),
                        group("$" + POST_ID, sum(NUM_VIEW, "$" + NUM_VIEW))))
                .iterator()
                .forEachRemaining(document -> postViewMap.put(
                        document.get(_ID).toString(),
                        createLong(document.get(NUM_VIEW).toString())));
        return postViewMap;
    }

    @Override
    public List<PostPageViewHourly> getNumViewPostByRangeTime(Long fromTime, Long toTime, Collection<String> postIds) {
        return mongoCollection
                .find(and(
                        gte(UPDATED_AT, fromTime),
                        lte(UPDATED_AT, toTime),
                        in(POST_ID, postIds)))
                .map(document -> new JsonObject(document).mapTo(PostPageViewHourly.class))
                .into(new ArrayList<>());
    }
}
