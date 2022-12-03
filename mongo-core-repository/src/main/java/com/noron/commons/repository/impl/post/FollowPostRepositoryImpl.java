package com.noron.commons.repository.impl.post;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.noron.commons.data.model.FollowPost;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.noron.commons.data.constant.GenericFieldConstant.POST_ID;

@Repository
public class FollowPostRepositoryImpl extends AbsMongoRepository<FollowPost> implements IFollowPostRepository {
    public FollowPostRepositoryImpl(MongoCollection<Document> followPostCollection) {
        super(followPostCollection);
    }

    @Override
    public List<FollowPost> getFollowedPost(String postId) {
        return execute(Filters.eq(POST_ID, postId));
    }
}
