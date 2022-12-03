package com.noron.commons.repository.impl.post;

import com.noron.commons.data.model.FollowPost;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IFollowPostRepository extends IMongoRepository<FollowPost> {
    List<FollowPost> getFollowedPost(String postId);
}
