package com.noron.commons.repository.impl.feed.impl;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.feed.FeedContent;
import com.noron.commons.repository.impl.feed.IFeedContentRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class FeedContentRepositoryImpl extends AbsFeedContentRepository<FeedContent> implements IFeedContentRepository {
    public FeedContentRepositoryImpl(MongoCollection<Document> feedContentCollection) {
        super(feedContentCollection);
    }
}
