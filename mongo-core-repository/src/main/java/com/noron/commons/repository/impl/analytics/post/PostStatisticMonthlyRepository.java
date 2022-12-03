package com.noron.commons.repository.impl.analytics.post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.statistic.PostStatisticMonthly;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class PostStatisticMonthlyRepository extends AbsMongoRepository<PostStatisticMonthly>
        implements IPostStatisticMonthlyRepository {

    public PostStatisticMonthlyRepository(MongoCollection<Document> postStatisticMonthlyCollection) {
        super(postStatisticMonthlyCollection);
    }
}
