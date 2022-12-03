package com.noron.commons.repository.impl.user;


import com.noron.commons.data.model.user.UserDeviceInfo;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IUserDeviceRepository extends IMongoRepository<UserDeviceInfo> {
    List<UserDeviceInfo> getByUserIds(List<String> userIds);
}
