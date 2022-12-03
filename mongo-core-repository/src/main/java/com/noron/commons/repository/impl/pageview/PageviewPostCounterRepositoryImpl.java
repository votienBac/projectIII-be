package com.noron.commons.repository.impl.pageview;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.noron.commons.data.model.analytics.PageViewPostCounter;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.gte;

@Repository
public class PageviewPostCounterRepositoryImpl extends AbsMongoRepository<PageViewPostCounter> implements IPageviewPostCounterRepository {

    public PageviewPostCounterRepositoryImpl(MongoCollection<Document> pageviewPostCounterCollection) {
        super(pageviewPostCounterCollection);
    }

    @Override
    public List<PageViewPostCounter> getTopPostFromGoogle(Integer limit) {
        return execute(gte("google", 100), Sorts.descending("google"), limit);
    }
}
