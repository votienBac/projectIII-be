package com.noron.commons.repository.impl.series;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.series.PostIndex;
import com.noron.commons.data.model.series.Series;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.noron.commons.data.constant.GenericFieldConstant.POST_INDICES;
import static com.noron.commons.data.constant.GenericFieldConstant._ID;
import static com.noron.commons.utils.ObjTransformUtils.objToDocument;

@Repository
public class SeriesRepositoryImpl extends AbsMongoRepository<Series> implements ISeriesRepository {
    public SeriesRepositoryImpl(MongoCollection<Document> seriesCollection) {
        super(seriesCollection);
    }

    @Override
    public void updatePostIndex(String seriesId, List<PostIndex> postIndices) {
        mongoCollection.updateOne(eq(_ID, seriesId), set(POST_INDICES, objToDocument(postIndices)));
    }
}
