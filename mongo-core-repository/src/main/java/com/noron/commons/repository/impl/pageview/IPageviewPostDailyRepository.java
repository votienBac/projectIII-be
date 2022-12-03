package com.noron.commons.repository.impl.pageview;


import com.noron.commons.data.model.analytics.PageViewPostDaily;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IPageviewPostDailyRepository extends IMongoRepository<PageViewPostDaily> {
    List<PageViewPostDaily> getCurrentDay();
}
