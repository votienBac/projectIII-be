package com.noron.commons.repository.impl;

import com.noron.commons.data.model.RawPostCrawler;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IRawPostCrawlerRepository extends IMongoRepository<RawPostCrawler> {

    RawPostCrawler getFirstNotYetPost();

    List<RawPostCrawler> getNotYetPost();

    void markAsPosted(String id, String postId);
}
