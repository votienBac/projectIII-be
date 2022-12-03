package com.noron.commons.repository.impl.session;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.constant.status.Status;
import com.noron.commons.data.model.session.Session;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.STATUS;
import static com.noron.commons.data.constant.GenericFieldConstant._ID;

@Repository
public class SessionRepositoryImpl extends AbsMongoRepository<Session> implements ISessionRepository {
    public SessionRepositoryImpl(MongoCollection<Document> sessionCollection) {
        super(sessionCollection);
    }

    @Override
    protected Bson filterActive() {
        return ne(STATUS, Status.DELETED);
    }

    @Override
    public void delete(String id) {
        mongoCollection.updateOne(eq(_ID, id), set(STATUS, Status.DELETED));
    }

}
