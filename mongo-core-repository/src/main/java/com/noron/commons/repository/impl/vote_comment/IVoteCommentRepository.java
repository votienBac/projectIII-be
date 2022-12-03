package com.noron.commons.repository.impl.vote_comment;

import com.noron.commons.data.model.post.vote.VoteComment;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;
import java.util.Map;

public interface IVoteCommentRepository extends IMongoRepository<VoteComment> {
    List<VoteComment> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);

    List<VoteComment> getByRangeTimeOfCommentIds(Long fromTime, Long toTime, List<String> postIds);

    Map<String, Long> aggVoteComment();
}
