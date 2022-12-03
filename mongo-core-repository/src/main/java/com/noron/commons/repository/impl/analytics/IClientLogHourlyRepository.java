package com.noron.commons.repository.impl.analytics;


import com.noron.commons.data.model.analytics.ClientLogHourly;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IClientLogHourlyRepository extends IMongoRepository<ClientLogHourly> {
    List<ClientLogHourly> getByUserIdFromTime(List<String> userIds, Long fromTime, Long toTime);

    List<ClientLogHourly> getByClientIdFromTime(List<String> clientIds, Long fromTime, Long toTime);

    void addUserId(String clientId, String userId);
}
