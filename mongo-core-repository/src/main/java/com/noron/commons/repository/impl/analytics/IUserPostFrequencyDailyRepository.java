package com.noron.commons.repository.impl.analytics;


import com.noron.commons.data.model.analytics.UserPostFrequency;
import com.noron.commons.data.model.analytics.UserPostFrequencyDaily;
import com.noron.commons.data.model.analytics.UserPostViewTime;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IUserPostFrequencyDailyRepository extends IMongoRepository<UserPostFrequencyDaily> {
    List<UserPostFrequencyDaily> getByUserIdsBeforeXDay(List<String> userIds, Integer numDayBefore);

    List<UserPostFrequencyDaily> getBeforeXDay(List<String> userIds, List<String> postIds, Integer numDayBefore);

    List<UserPostFrequency> collectUserPostFrequency(List<String> userIds, Long startTime, Long endTime);

    List<String> getPostIdsOverPeriod(Long fromTime, Long endTime);

    List<UserPostFrequencyDaily> getPostsOverPeriod(List<String> postIds,
                                                    List<String> ownerIds,
                                                    Long fromTime,
                                                    Long endTime);

    List<UserPostViewTime> getUserPostViewTime(List<String> ids);
}
