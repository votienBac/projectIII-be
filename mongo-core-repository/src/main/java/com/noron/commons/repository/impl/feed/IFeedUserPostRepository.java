package com.noron.commons.repository.impl.feed;

import com.noron.commons.data.model.feed.UserPostScore;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IFeedUserPostRepository extends IMongoRepository<UserPostScore> {
    void deletedOldDataOfUserByTime(String userId, Long lastTime);

    void deletedOldDataOfUserIsByTime(List<String> userIds, Long lastTime);

    void deletedOldDataByTime(Long lastTime);

    void incOrderItemsOfUser(String userId, List<String> postIds, Integer incOrder);
}
