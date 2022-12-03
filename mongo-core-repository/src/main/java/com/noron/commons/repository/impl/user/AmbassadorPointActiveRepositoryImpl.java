package com.noron.commons.repository.impl.user;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.user.AmbassadorPointActive;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import static com.noron.commons.data.constant.GenericFieldConstant.CREATION_TIME;

@Repository
public class AmbassadorPointActiveRepositoryImpl
        extends AbsMongoRepository<AmbassadorPointActive>
        implements IAmbassadorPointActiveRepository {

    public AmbassadorPointActiveRepositoryImpl(MongoCollection<Document> ambassadorPointActiveCollection) {
        super(ambassadorPointActiveCollection);
    }

    @Override
    protected String getFieldRangeTime() {
        return CREATION_TIME;
    }
}
