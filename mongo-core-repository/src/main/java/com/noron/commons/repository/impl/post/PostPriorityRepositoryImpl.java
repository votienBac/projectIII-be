package com.noron.commons.repository.impl.post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.PostPriority;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.noron.commons.data.constant.GenericFieldConstant.POST_ID;

@Repository
public class PostPriorityRepositoryImpl extends AbsMongoRepository<PostPriority> implements IPostPriorityRepository {
    public PostPriorityRepositoryImpl(MongoCollection<Document> postPriorityCollection) {
        super(postPriorityCollection);
    }

    @Override
    public List<PostPriority> getPostPriority(String postId) {
        return execute(eq(POST_ID, postId));
    }
}
