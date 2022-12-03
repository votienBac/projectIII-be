package com.noron.commons.repository.impl.series;

import com.noron.commons.data.model.series.PostIndex;
import com.noron.commons.data.model.series.Series;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface ISeriesRepository extends IMongoRepository<Series> {
    void updatePostIndex(String seriesId, List<PostIndex> postIndices);
}
