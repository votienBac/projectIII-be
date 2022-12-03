package com.noron.commons.repository.impl.view_post;

import com.noron.commons.data.model.post.view.ViewPost;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IViewPostRepository extends IMongoRepository<ViewPost> {
    List<ViewPost> getByRangeTimeOfUsers(Long fromTime, Long toTime, List<String> userIds);

    List<ViewPost> getByRangeTimeIgnoreUsers(Long fromTime, Long toTime, List<String> ignoreUserIds);
}
