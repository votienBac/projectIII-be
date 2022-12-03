package com.noron.commons.repository.impl.post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.UserHistorySearch;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class UserHistorySearchRepositoryImpl extends AbsMongoRepository<UserHistorySearch> implements IUserHistorySearchRepository {
    public UserHistorySearchRepositoryImpl(MongoCollection<Document> userHistorySearchCollection) {
        super(userHistorySearchCollection);
    }
}
