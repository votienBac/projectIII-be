package com.noron.commons.repository.impl.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.noron.commons.data.model.user.UserDeviceInfo;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.noron.commons.data.constant.GenericFieldConstant.USER_ID;

@Repository
public class UserDeviceInfoRepositoryImpl extends AbsMongoRepository<UserDeviceInfo> implements IUserDeviceRepository {

    public UserDeviceInfoRepositoryImpl(MongoCollection<Document> userDeviceInfoCollection) {
        super(userDeviceInfoCollection);
    }

    @Override
    public List<UserDeviceInfo> getByUserIds(List<String> userIds) {
        return execute(Filters.in(USER_ID, userIds));
    }
}
