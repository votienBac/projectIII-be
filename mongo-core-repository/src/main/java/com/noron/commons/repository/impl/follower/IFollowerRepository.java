package com.noron.commons.repository.impl.follower;

import com.noron.commons.data.model.follow.Follower;

import java.util.List;
import java.util.Map;

public interface IFollowerRepository {
    Map<String, Integer> countFollowerMap(List<String> var1);

    Boolean checkFollowedStatus(String var1, String var2);

    String followUser(Follower var1);

    List<Follower> getFollowers(List<String> userIds);
}
