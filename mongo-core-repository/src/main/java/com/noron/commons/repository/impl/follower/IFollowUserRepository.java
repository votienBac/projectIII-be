package com.noron.commons.repository.impl.follower;

public interface IFollowUserRepository {
    String followUser(String var1, String var2);

    Boolean checkFollowedStatus(String var1, String var2);
}
