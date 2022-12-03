package com.noron.commons.repository.impl.post;


import com.noron.commons.data.model.Post;
import com.noron.commons.data.model.post.ShortPost;
import com.noron.commons.repository.IMongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IPostRepository extends IMongoRepository<Post> {
    void updatePost(Post post);

    List<Post> getByIdsOfTopic(Collection<String> ids, String topicIds);

    Long countContentOfUser(String userId, String postType);

    Map<String, Long> countContentOfUsers(List<String> userIds, String postType);

    List<Post> getByIds(List<String> id, String postType);

    List<Post> getByRangeTime(Long fromTime, Long toTime, String postType);

    List<Post> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);

    List<Post> getByRangeTimeIgnoreUserIds(Long fromTime, Long toTime, List<String> userIds);

    List<Post> getQuestionHasHotComment(List<String> topicIds, List<String> blacklistUserIds, Integer numPost);

    List<Post> getQuestionNotYetAnswer(List<String> topicIds, List<String> blacklistUserIds, Integer numPost);

    List<Post> getPostsOfTopic(List<String> postIds, String topicId);

    List<Post> getByPostType(List<String> topicIds, List<String> blacklistUserIds, String postType, Integer numPost);

    List<Post> getPostBySeriesId(String seriesId);

    List<Post> getPostOfSeeder(List<String> seederIds, String post_type);

    void updateSeriesId(Collection<String> postIds, String seriesId);

    void unsetSeriesId(List<String> postIds);

    void incNumFollower(String postId, Integer numInc);


    List<Post> getActivePostByIds(List<String> postIds);

    List<Post> getByUserId(String userId);

    void incNumView(List<String> postIds, Integer numView);

    List<String> getAllPostIds();

    List<String> getAllBlogIds();

    Map<String, List<String>> getPostIdTopicIdMap(String postType);

    void incNumVote(List<String> postIds, Integer numVote);

    List<Post> getActivePostsBlocking(Long fromDate, Long toDate, List<String> topicIds, String postType);

    List<ShortPost> getShortPostByIds(List<String> postIds);
}
