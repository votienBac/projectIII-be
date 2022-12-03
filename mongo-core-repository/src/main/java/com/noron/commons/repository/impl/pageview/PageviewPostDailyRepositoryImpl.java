package com.noron.commons.repository.impl.pageview;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.analytics.PageViewPostDaily;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.noron.commons.data.constant.GenericFieldConstant.UPDATED_AT;
import static com.noron.commons.utils.TimeUtil.getCurrentTimeLong;
import static com.noron.commons.utils.TimeUtil.getStartTimeOfDay;

@Repository
public class PageviewPostDailyRepositoryImpl extends AbsMongoRepository<PageViewPostDaily> implements IPageviewPostDailyRepository {

    public PageviewPostDailyRepositoryImpl(MongoCollection<Document> pageviewPostDailyCollection) {
        super(pageviewPostDailyCollection);
    }

    @Override
    public List<PageViewPostDaily> getCurrentDay() {
        return execute(and(gte(UPDATED_AT, getStartTimeOfDay()), lte(UPDATED_AT, getCurrentTimeLong())));
    }
}