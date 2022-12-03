package com.noron.commons.repository.impl.analytics;


import com.noron.commons.data.model.analytics.PostPageViewHourly;
import com.noron.commons.repository.IMongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IPostPageViewHourlyRepository extends IMongoRepository<PostPageViewHourly> {
    List<PostPageViewHourly> getBySessionId(String sessionId);

    Map<String, Long> countNumViewFromTime(Long fromTime, Long toTime);

    Map<String, Long> countNumView(List<String> postIds);

    Map<String, Long> countNumViewPostByRangeTime(Long fromTime, Long toTime, List<String> postIds);

    List<PostPageViewHourly> getNumViewPostByRangeTime(Long fromTime, Long toTime, Collection<String> postIds);
}
