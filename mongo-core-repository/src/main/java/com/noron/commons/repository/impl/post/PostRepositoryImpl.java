package com.noron.commons.repository.impl.post;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.noron.commons.data.model.Post;
import com.noron.commons.data.model.SearchRequest;
import com.noron.commons.data.model.post.ShortPost;
import com.noron.commons.repository.AbsMongoRepository;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.ObjectType.BLOG;
import static com.noron.commons.data.constant.ObjectType.QUESTION;
import static com.noron.commons.data.constant.status.Status.ACTIVE;
import static com.noron.commons.utils.ObjTransformUtils.toDocUpdate;
import static com.noron.commons.utils.PostUtils.decodePostId;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.math.NumberUtils.createLong;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class PostRepositoryImpl extends AbsMongoRepository<Post> implements IPostRepository {

    public PostRepositoryImpl(MongoCollection<Document> postCollection) {
        super(postCollection);
    }

    @Override
    public void updatePost(Post post) {
        mongoCollection.updateOne(
                eq(_ID, post.getId()),
                combine(toDocUpdate(post, _ID)));
    }

    @Override
    public void incNumFollower(String postId, Integer numInc) {
        mongoCollection.updateOne(
                eq(_ID, postId),
                inc(NUM_FOLLOWER, numInc));
    }

    @Override
    public Post save(Post post) {
        post.setVotePoint(0);
        post.setNumUpVote(0);
        post.setNumDownVote(0);
        super.save(post);
        return post;
    }

    @Override
    public List<Post> getByIdsOfTopic(Collection<String> ids, String topicId) {
        return execute(and(in(_ID, ids), eq(TOPIC_IDS, topicId)));
    }

    @Override
    protected Bson filterActive() {
        return and(eq(STATUS, ACTIVE), nin(TOPIC_IDS, "98355434850628658", "98638123138812592"));
    }

    protected Bson buildSearchQueries(SearchRequest request) {
        final String keyword = request.getKeyword();
        if (StringUtils.isNotEmpty(keyword)) {
            final String postId = decodePostId(keyword);
            if (postId != null) {
                return Filters.or(Filters.regex(TITLE, ".*" + keyword + ".*"), Filters.eq(_ID, postId));
            }
            return Filters.regex(TITLE, ".*" + keyword + ".*");
        }
        return super.buildSearchQueries(request);
    }

    @Override
    public Long countContentOfUser(String userId, String postType) {
        return count(eq(OWNER_ID, userId), eq(POST_TYPE, postType), filterActive());
    }

    @Override
    public Map<String, Long> countContentOfUsers(List<String> userIds, String postType) {
        final HashMap<String, Long> result = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(in(OWNER_ID, userIds)),
                        match(eq(POST_TYPE, postType)),
                        match(filterActive()),
                        group("$" + OWNER_ID, sum("total", 1))
                ))
                .iterator()
                .forEachRemaining(document -> {
                    final String userId = document.get(_ID).toString();
                    final Long total = createLong(document.get("total").toString());
                    result.put(userId, total);
                });
        return result;
    }

    @Override
    public List<Post> getActivePostByIds(List<String> postIds) {
        return execute(and(in(_ID, postIds), eq(STATUS, ACTIVE)));
    }

    @Override
    public List<Post> getByIds(List<String> id, String postType) {
        return mongoCollection.find(and(in(_ID, id), eq(POST_TYPE, postType)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getByRangeTime(Long fromTime, Long toTime, String postType) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(POST_TYPE, postType),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(OWNER_ID, userIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getByRangeTimeIgnoreUserIds(Long fromTime, Long toTime, List<String> ignoreUserIds) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        nin(OWNER_ID, ignoreUserIds),
                        gte(getFieldRangeTime(), fromTime),
                        lte(getFieldRangeTime(), toTime)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getQuestionHasHotComment(List<String> topicIds, List<String> blacklistUserIds, Integer numPost) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(TOPIC_IDS, topicIds),
                        eq(POST_TYPE, QUESTION),
                        nin(OWNER_ID, blacklistUserIds),
                        ne(HOT_COMMENT_ID, null)))
                .sort(Sorts.descending(CREATION_TIME))
                .limit(numPost)
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getQuestionNotYetAnswer(List<String> topicIds, List<String> blacklistUserIds, Integer numPost) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(TOPIC_IDS, topicIds),
                        eq(POST_TYPE, QUESTION),
                        nin(OWNER_ID, blacklistUserIds),
                        eq(NUM_COMMENT, 0)))
                .sort(Sorts.descending(CREATION_TIME))
                .limit(numPost)
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getPostsOfTopic(List<String> postIds, String topicId) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(TOPIC_IDS, topicId),
                        in(_ID, postIds)))
                .sort(Sorts.descending(CREATION_TIME))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getByPostType(List<String> topicIds, List<String> blacklistUserIds, String postType, Integer numPost) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(TOPIC_IDS, topicIds),
                        nin(OWNER_ID, blacklistUserIds),
                        eq(POST_TYPE, postType)))
                .sort(Sorts.descending(CREATION_TIME))
                .limit(numPost)
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getPostBySeriesId(String seriesId) {
        return mongoCollection
                .find(and(eq(SERIES_ID, seriesId)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Post> getPostOfSeeder(List<String> seederIds, String type) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        in(OWNER_ID, seederIds),
                        eq(POST_TYPE, type)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public void updateSeriesId(Collection<String> postIds, String seriesId) {
        mongoCollection.updateMany(in(_ID, postIds), set(SERIES_ID, seriesId));
    }

    @Override
    public void unsetSeriesId(List<String> postIds) {
        mongoCollection.updateMany(in(_ID, postIds), unset(SERIES_ID));
    }

    @Override
    public List<Post> getByUserId(String userId) {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(OWNER_ID, userId)))
                .map(document -> new JsonObject(document).mapTo(Post.class))
                .into(new ArrayList<>());
    }

    @Override
    public void incNumView(List<String> postIds, Integer numView) {
        mongoCollection.updateMany(
                in(_ID, postIds),
                inc(NUM_VIEW, numView));
    }

    @Override
    public List<String> getAllPostIds() {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(STATUS, 1)))
                .projection(include(_ID))
                .map(document -> document.get(_ID).toString())
                .into(new ArrayList<>());
    }

    @Override
    public List<String> getAllBlogIds() {
        return mongoCollection
                .find(and(
                        filterActive(),
                        eq(STATUS, 1),
                        eq(POST_TYPE, BLOG)))
                .projection(include(_ID))
                .map(document -> document.get(_ID).toString())
                .into(new ArrayList<>());
    }

    @Override
    public Map<String, List<String>> getPostIdTopicIdMap(String postType) {
        final HashMap<String, List<String>> result = new HashMap<>();
        mongoCollection
                .aggregate(asList(
                        match(eq(POST_TYPE, postType)),
                        match(filterActive()),
                        group("$" + _ID)))
                .iterator()
                .forEachRemaining(document -> {
                    final String postId = document.get(_ID).toString();
                    result.put(postId, (List<String>) document.get("topic_ids"));
                });
        return result;
    }

    @Override
    public void incNumVote(List<String> postIds, Integer numVote) {
        mongoCollection.updateMany(
                in(_ID, postIds),
                inc(NUM_UP_VOTE, numVote));
    }


    @Override
    public List<Post> getActivePostsBlocking(Long fromDate, Long toDate, List<String> topicIds, String postType) {
        if (fromDate == null || toDate == null || isEmpty(topicIds)) return new ArrayList<>();
        return execute(
                in(TOPIC_IDS, topicIds),
                eq(POST_TYPE, postType),
                eq(STATUS, ACTIVE),
                gte(CREATION_TIME, fromDate),
                lte(CREATION_TIME, toDate));
    }

    public Map<String, Integer> getActiveUnwindPosts() {

        Map<String, Integer> tagsMap = new HashMap<>();

        this.mongoCollection
                .aggregate(asList(
                        match(filterActive()),
                        unwind("$tags"),
                        group("$tags", Accumulators.sum("count", 1))))
                .batchSize(100)
                .iterator()
                .forEachRemaining(document -> {
                    if (!document.getString("_id").equals("")) {
                        tagsMap.put(document.getString("_id"), document.getInteger("count"));
                    }
                });

        return tagsMap;
    }

    @Override
    public List<ShortPost> getShortPostByIds(List<String> postIds) {
        return mongoCollection
                .find(Filters.in(_ID, postIds))
                .projection(Projections.include(_ID, OWNER_ID, POST_TYPE, CREATION_TIME, TITLE))
                .map(document -> new JsonObject(document).mapTo(ShortPost.class))
                .into(new ArrayList<>());
    }
}
