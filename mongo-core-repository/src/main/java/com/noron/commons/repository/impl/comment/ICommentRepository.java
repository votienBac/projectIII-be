package com.noron.commons.repository.impl.comment;


import com.noron.commons.data.model.comment.Comment;
import com.noron.commons.repository.IMongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ICommentRepository extends IMongoRepository<Comment> {
    List<Comment> getByPostIds(Collection<String> postIds);

    List<Comment> getByType(String type);

    List<Comment> getByPostId(String postId);

    List<Comment> getByParentIds(Collection<String> parentIds);

    List<Comment> getAnswersOfPosts(Collection<String> postIds);

    Long countContentOfUser(String userId, String type);

    Map<String, Long> countContentOfUsers(List<String> userIds, String type);

    List<Comment> getByPostIdPostType(String postId, String... postTypes);

    List<Comment> getByRangeTime(Long fromTime, Long toTime, String type);

    List<Comment> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);

    List<Comment> getCommentOfSeeder(List<String> seederIds);

    List<Comment> getByUserId(String userId);

    List<String> getCommentIds(String type);

    void incNumVote(List<String> commentIds, Integer numVote);


}
