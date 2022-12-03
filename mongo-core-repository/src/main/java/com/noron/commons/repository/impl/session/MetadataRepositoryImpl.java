package com.noron.commons.repository.impl.session;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.session.Metadata;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.utils.TimeUtil.getCurrentTimeLong;

@Repository
public class MetadataRepositoryImpl extends AbsMongoRepository<Metadata> implements IMetadataRepository {
    public MetadataRepositoryImpl(MongoCollection<Document> metadataCollection) {
        super(metadataCollection);
    }

    @Override
    public void delete(String id) {
        mongoCollection.updateOne(eq(_ID, id), set(DELETED_AT, getCurrentTimeLong()));
    }

    @Override
    public List<Metadata> getBySessionId(String sessionId) {
        return execute(and(eq(SESSION_ID, sessionId), filterActive()));
    }

    @Override
    public List<Metadata> getBySessionIds(List<String> sessionIds) {
        return execute(and(in(SESSION_ID, sessionIds), filterActive()));
    }

    @Override
    public void deletedBySessionId(String sessionId) {
        mongoCollection.deleteMany(eq(SESSION_ID, sessionId));
    }

    @Override
    public void deletedByIds(List<String> ids, String userId) {
        mongoCollection.updateMany(
                in(_ID, ids),
                combine(
                        set(DELETED_AT, getCurrentTimeLong()),
                        set(DELETED_BY, userId)));
    }

    @Override
    protected Bson filterActive() {
        return eq(DELETED_AT, null);
    }
}
