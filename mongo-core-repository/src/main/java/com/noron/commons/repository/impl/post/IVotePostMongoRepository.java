package com.noron.commons.repository.impl.post;


import com.noron.commons.data.model.post.vote.VotePost;
import com.noron.commons.repository.IMongoRepository;

public interface IVotePostMongoRepository extends IMongoRepository<VotePost> {
    VotePost getByCommentIdOfUser(String ownerId, String postId);

    boolean updateHistoryVote(String ownerId, String postId);
}
