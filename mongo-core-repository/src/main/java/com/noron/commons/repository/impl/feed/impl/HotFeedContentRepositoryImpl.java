package com.noron.commons.repository.impl.feed.impl;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.feed.FeedContent;
import com.noron.commons.repository.impl.feed.IHotFeedContentRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class HotFeedContentRepositoryImpl extends AbsFeedContentRepository<FeedContent> implements IHotFeedContentRepository {

    public HotFeedContentRepositoryImpl(MongoCollection<Document> hotFeedContentCollection) {
        super(hotFeedContentCollection);
    }
}
