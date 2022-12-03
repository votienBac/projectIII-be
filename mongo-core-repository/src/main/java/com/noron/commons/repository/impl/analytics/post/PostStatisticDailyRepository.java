package com.noron.commons.repository.impl.analytics.post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.statistic.PostStatisticDaily;
import com.noron.commons.data.model.post.statistic.PostStatisticMonthly;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.group;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static java.util.Arrays.asList;

@Repository
public class PostStatisticDailyRepository extends AbsMongoRepository<PostStatisticDaily>
        implements IPostStatisticDailyRepository {

    public PostStatisticDailyRepository(MongoCollection<Document> postStatisticDailyCollection) {
        super(postStatisticDailyCollection);
    }

    @Override
    public List<PostStatisticMonthly> statisticMonth() {
        return mongoCollection
                .aggregate(asList(
                        group(
                                new Document("post_id", "$post_id").append("month", "$month").append("year", "$year"),
                                sum(NUM_VIEW, "$".concat(NUM_VIEW)),
                                sum(NUM_ANSWER, "$".concat(NUM_ANSWER)),
                                sum(NUM_COMMENT, "$".concat(NUM_COMMENT)),
                                sum("num_vote", "$num_vote"),
                                sum("num_user_vote", "$num_user_vote"),
                                sum("num_user_view", "$num_user_view"),
                                max(CREATION_TIME, "$".concat(CREATION_TIME)),
                                last("year", "$year"),
                                last("topic_ids", "$topic_ids"),
                                last("session_id", "$session_id"),
                                last("series_id", "$series_id"),
                                last("post_type", "$post_type"),
                                last("month", "$month"),
                                last(OWNER_ID, "$".concat(OWNER_ID)),
                                last(POST_ID, "$".concat(POST_ID)))))
                .map(document -> {
                    document.remove("_id");
                    return JsonObject.mapFrom(document).mapTo(PostStatisticMonthly.class);
                })
                .into(new ArrayList<>());
    }
}
