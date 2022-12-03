package com.noron.commons.repository.impl.comment;

import com.noron.commons.data.model.post.vote.VoteComment;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IVoteCommentMongoRepository extends IMongoRepository<VoteComment> {
    VoteComment getByCommentIdOfUser(String ownerId, String commentId);

    boolean updateHistoryVote(String ownerId, String commentId);

    List<VoteComment> getByCommentId(String commentId);
}
