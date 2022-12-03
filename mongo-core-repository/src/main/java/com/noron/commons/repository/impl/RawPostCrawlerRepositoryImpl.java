package com.noron.commons.repository.impl;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.RawPostCrawler;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.utils.TimeUtil.getCurrentTimeLong;

@Repository
public class RawPostCrawlerRepositoryImpl extends AbsMongoRepository<RawPostCrawler> implements IRawPostCrawlerRepository {

    public RawPostCrawlerRepositoryImpl(MongoCollection<Document> rawPostCrawlerCollection) {
        super(rawPostCrawlerCollection);
    }

    @Override
    public RawPostCrawler getFirstNotYetPost() {
        return getFirst(ne(IS_POSTED, true));
    }

    @Override
    public List<RawPostCrawler> getNotYetPost() {
        return execute(ne(IS_POSTED, true));
    }

    @Override
    public void markAsPosted(String id, String postId) {
        mongoCollection.updateOne(
                eq(_ID, id),
                combine(set(IS_POSTED, true),
                        set(POST_ID, postId),
                        set(POSTED_AT, getCurrentTimeLong())));
    }
}
