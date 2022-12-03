package com.noron.commons.repository.impl.post;

import com.noron.commons.data.model.PostPriority;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IPostPriorityRepository extends IMongoRepository<PostPriority> {
    List<PostPriority> getPostPriority(String postId);
}
