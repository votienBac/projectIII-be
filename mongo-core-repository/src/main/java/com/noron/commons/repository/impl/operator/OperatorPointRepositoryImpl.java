package com.noron.commons.repository.impl.operator;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.operator.OperatorPoint;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class OperatorPointRepositoryImpl extends AbsMongoRepository<OperatorPoint> implements IOperatorPointRepository {
    public OperatorPointRepositoryImpl(MongoCollection<Document> operatorPointCollection) {
        super(operatorPointCollection);
    }
}
