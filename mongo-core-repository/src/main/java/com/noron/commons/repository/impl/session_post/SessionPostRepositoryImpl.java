package com.noron.commons.repository.impl.session_post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.constant.status.Status;
import com.noron.commons.data.model.session.SessionPost;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.STATUS;
import static com.noron.commons.data.constant.GenericFieldConstant._ID;

@Repository
public class SessionPostRepositoryImpl extends AbsMongoRepository<SessionPost> implements ISessionPostRepository {
    public SessionPostRepositoryImpl(MongoCollection<Document> sessionPostCollection) {
        super(sessionPostCollection);
    }

    @Override
    public void delete(String id) {
        mongoCollection.updateOne(eq(_ID, id), set(STATUS, Status.DELETED));
    }
}
