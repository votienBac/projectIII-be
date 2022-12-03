package com.noron.commons.repository.impl.operator;


import com.mongodb.Function;
import com.noron.commons.data.model.operator.MustAnswer;
import com.noron.commons.repository.IMongoRepository;

import java.util.Collection;
import java.util.List;

public interface IMustAnswerRepository extends IMongoRepository<MustAnswer> {
    void deleteNotInRangTime(Long startTime, Long endTime, Collection<String> postIds);

    void deleteBlog();

    void saveMany(List<MustAnswer> mustAnswers, Function<MustAnswer, Boolean> upsert);

    MustAnswer update(MustAnswer mustAnswer);

    void deleteByPostId(String postId);

    List<MustAnswer> getMustAnswers(List<String> blackListUserIds);

    List<MustAnswer> getByOperatorIds(String operatorId);

    List<MustAnswer> getByOperatorId(String operatorId);

}
