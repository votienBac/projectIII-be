package com.noron.commons.repository.impl.feed.impl;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.feed.FeedContent;
import com.noron.commons.repository.impl.feed.IForYouFeedContentRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class ForYouContentRepositoryImpl extends AbsFeedContentRepository<FeedContent> implements IForYouFeedContentRepository {
    public ForYouContentRepositoryImpl(MongoCollection<Document> forYouFeedContentCollection) {
        super(forYouFeedContentCollection);
    }
}
