package com.noron.commons.repository.impl.view_post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.view.ViewPost;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.USER_ID;

@Repository
public class ViewPostRepositoryImpl extends AbsMongoRepository<ViewPost> implements IViewPostRepository {
    public ViewPostRepositoryImpl(MongoCollection<Document> viewPostCollection) {
        super(viewPostCollection);
    }

    @Override
    public List<ViewPost> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        in(USER_ID, userIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(ViewPost.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<ViewPost> getByRangeTimeIgnoreUsers(Long fromTime, Long toTime, List<String> ignoreUserIds) {
        return mongoCollection
                .find(and(
                        nin(USER_ID, ignoreUserIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(ViewPost.class))
                .into(new ArrayList<>());
    }

    @Override
    protected String getFieldRangeTime() {
        return "last_view_post_time";
    }
}
