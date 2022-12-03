package com.noron.commons.repository.impl.session;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.session.Session;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class SessionCommonRepository extends AbsMongoRepository<Session> {
    public SessionCommonRepository(MongoCollection<Document> sessionCollection) {
        super(sessionCollection);
    }

}
