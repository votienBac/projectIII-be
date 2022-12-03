package com.noron.commons.repository.impl.user;

import com.noron.commons.data.model.user.UserActivePoint;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IUserActivePointRepository extends IMongoRepository<UserActivePoint> {
    List<UserActivePoint> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);
}
