package com.noron.commons.repository.impl.operator;


import com.mongodb.Function;
import com.noron.commons.data.model.comment.Comment;
import com.noron.commons.data.model.operator.MustComment;
import com.noron.commons.repository.IMongoRepository;

import java.util.Collection;
import java.util.List;

public interface IMustCommentRepository extends IMongoRepository<MustComment> {
    void deleteNotInRangTime(Long startTime, Long endTime, Collection<String> commentIds);

    void deleteNotAcceptType();

    void deleteByPostIds(List<String> postIds, String type);

    void saveMany(List<MustComment> mustAnswers, Function<MustComment, Boolean> upsert);

    void deleteByPostId(String postId);

    void deleteByCommentId(Comment comment);

    Long countMustCommentDoneThisDay(List<String> topicIds,
                                     List<String> blackListUserIds);

    Long countContentNotDone(List<String> topicIds,
                             List<String> blackListUserIds);

    List<MustComment> getMustComments(List<String> blackListUserIds);

    void incNumMustComment(String commentId, String postId);

    List<MustComment> getMustComments(List<String> blackListUserIds, Long from, Long to);

    List<MustComment> getByOperatorIds(String operatorId);

    List<MustComment> getByOperatorId(String operatorId);
}
