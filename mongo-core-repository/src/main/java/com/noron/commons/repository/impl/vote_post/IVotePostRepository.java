package com.noron.commons.repository.impl.vote_post;

import com.noron.commons.data.model.post.vote.VotePost;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;
import java.util.Map;

public interface IVotePostRepository extends IMongoRepository<VotePost> {
    List<VotePost> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);

    List<VotePost> getByRangeTimeOfPosts(Long fromTime, Long toTime, List<String> postIds);

    VotePost getByPostOwnerId(String postId, String ownerId);

    Map<String, Long> aggVotePost();
}
