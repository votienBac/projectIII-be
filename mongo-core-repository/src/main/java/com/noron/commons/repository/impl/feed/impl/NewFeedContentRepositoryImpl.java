package com.noron.commons.repository.impl.feed.impl;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.feed.FeedContent;
import com.noron.commons.repository.impl.feed.INewFeedContentRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class NewFeedContentRepositoryImpl extends AbsFeedContentRepository<FeedContent> implements INewFeedContentRepository {

    public NewFeedContentRepositoryImpl(MongoCollection<Document> newFeedContentCollection) {
        super(newFeedContentCollection);
    }
}
