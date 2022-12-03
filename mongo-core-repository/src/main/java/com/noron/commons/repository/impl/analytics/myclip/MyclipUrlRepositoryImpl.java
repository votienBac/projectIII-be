package com.noron.commons.repository.impl.analytics.myclip;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.analytics.MyClipUrl;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class MyclipUrlRepositoryImpl extends AbsMongoRepository<MyClipUrl> implements IMyclipUrlRepository {

    public MyclipUrlRepositoryImpl(MongoCollection<Document> myclipUrlCollection) {
        super(myclipUrlCollection);
    }
}
