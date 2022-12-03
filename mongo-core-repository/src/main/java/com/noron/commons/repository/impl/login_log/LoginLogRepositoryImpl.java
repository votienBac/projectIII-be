package com.noron.commons.repository.impl.login_log;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.LoginLog;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogRepositoryImpl extends AbsMongoRepository<LoginLog>
        implements ILoginLogRepository {
    public LoginLogRepositoryImpl(MongoCollection<Document> loginLogCollection) {
        super(loginLogCollection);
    }
}
