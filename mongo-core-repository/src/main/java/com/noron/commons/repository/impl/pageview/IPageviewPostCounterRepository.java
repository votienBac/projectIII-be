package com.noron.commons.repository.impl.pageview;


import com.noron.commons.data.model.analytics.PageViewPostCounter;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IPageviewPostCounterRepository extends IMongoRepository<PageViewPostCounter> {
    List<PageViewPostCounter> getTopPostFromGoogle(Integer limit);
}
