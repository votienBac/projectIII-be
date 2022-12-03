package com.noron.commons.repository.impl.post;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.vote.VotePost;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.status.BotVoteHistoryStatus.voted;

@Repository
public class VotePostMongoRepositoryImpl extends AbsMongoRepository<VotePost> implements IVotePostMongoRepository {
    public VotePostMongoRepositoryImpl(MongoCollection<Document> votePostCollection) {
        super(votePostCollection);
    }

    @Override
    public VotePost getByCommentIdOfUser(String ownerId, String postId) {
        return getFirst(and(eq(OWNER_ID, ownerId), eq(POST_ID, postId)));
    }

    @Override
    public boolean updateHistoryVote(String ownerId, String postId) {
        mongoCollection.updateOne(
                and(
                        eq(OWNER_ID, ownerId),
                        eq(POST_ID, postId)),
                set(HISTORY_VOTE, voted.name()));
        return false;
    }
}
