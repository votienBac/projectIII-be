package com.noron.commons.repository.impl.meeting;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.constant.status.Status;
import com.noron.commons.data.model.meeting.Meeting;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.PostConstant.ACTIVE;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;

@Repository
public class MeetingRepositoryImpl extends AbsMongoRepository<Meeting> implements IMeetingRepository {
    public MeetingRepositoryImpl(MongoCollection<Document> meetingCollection) {
        super(meetingCollection);
    }

    @Override
    protected Bson filterActive() {
        return eq(STATUS, Status.ACTIVE);
    }

    @Override
    public String getPageView(String meetingId) {
        return mongoCollection
                .find(and(eq(_ID, meetingId), eq(STATUS, ACTIVE)))
                .map(document -> {
                    if ((document.get(NUM_PAGEVEW) != null)) {
                        return document.get(NUM_PAGEVEW).toString();
                    } else
                        return "0";
                }).first();
    }

    @Override
    public Integer updateNumView(String meetingId, Integer numView) {
        mongoCollection.updateOne(eq(_ID, meetingId), set(NUM_PAGEVEW, numView));
        return numView;
    }

    @Override
    public Map<String, Integer> getNumView(List<String> meetingIds) {
        final HashMap<String, Integer> result = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(in(MEETING_ID, meetingIds)),
                        match(filterActive())
                ))
                .iterator()
                .forEachRemaining(document -> {
                    final String userId = document.get(_ID).toString();
                    final Integer total = createInteger(document.get(NUM_VIEW).toString());
                    result.put(userId, total);
                });
        return result;
    }

}
