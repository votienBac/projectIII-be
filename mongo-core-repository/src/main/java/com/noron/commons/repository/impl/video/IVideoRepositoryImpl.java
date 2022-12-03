package com.noron.commons.repository.impl.video;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.video.Video;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.*;
import static com.noron.commons.data.constant.status.Status.DELETED;
import static com.noron.commons.utils.TimeUtil.getCurrentTimeLong;


@Repository
public class IVideoRepositoryImpl extends AbsMongoRepository<Video> implements IVideoRepository {
    public IVideoRepositoryImpl(MongoCollection<Document> videoCollection) {
        super(videoCollection);
    }

    @Override
    public void delete(String id) {
        mongoCollection.updateOne(eq(_ID, id), combine(
                set(DELETED_AT, getCurrentTimeLong()),
                set(STATUS, DELETED)));
    }
}
