package com.noron.commons.repository.impl.feed;

import com.noron.commons.data.model.feed.FeedContent;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IBaseFeedContentRepository extends IMongoRepository<FeedContent> {
    void deletedOldDataOfUserByTime(String userId, Long lastTime);

    void deletedOldDataOfUserIsByTime(List<String> userIds, Long lastTime);

    void deletedOldDataByTime(Long lastTime);

    List<String> incOrderItemsOfUser(String userId, List<String> postIds, Integer incOrder);
}
