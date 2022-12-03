package com.noron.commons.repository.impl.comment;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.post.vote.VoteComment;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.status.BotVoteHistoryStatus.voted;

@Repository
public class VoteCommentMongoRepositoryImpl extends AbsMongoRepository<VoteComment> implements IVoteCommentMongoRepository {
    public VoteCommentMongoRepositoryImpl(MongoCollection<Document> voteCommentCollection) {
        super(voteCommentCollection);
    }

    @Override
    public VoteComment getByCommentIdOfUser(String ownerId, String commentId) {
        return getFirst(and(
                eq(OWNER_ID, ownerId),
                eq(COMMENT_ID, commentId)));
    }

    @Override
    public boolean updateHistoryVote(String ownerId, String commentId) {
        mongoCollection.updateOne(and(
                eq(OWNER_ID, ownerId),
                eq(COMMENT_ID, commentId)),
                set(HISTORY_VOTE, voted.name()));
        return false;
    }

    @Override
    public List<VoteComment> getByCommentId(String commentId) {
        return execute(eq(COMMENT_ID, commentId));
    }
}
