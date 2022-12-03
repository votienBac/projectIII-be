package vn.noron.caching.user;

import io.reactivex.rxjava3.core.Single;
import vn.noron.caching.BaseSetCaching;
import vn.noron.caching.config.annotation.CacheConfig;
import vn.noron.caching.config.redis.JedisConfigCommon;
import vn.noron.data.tables.pojos.User;
import vn.noron.repository.user.IUserRepository;

import java.util.Optional;

@CacheConfig(pattern = "{noron.user.info}:%s", expireSecond = 3600)
public class UserInfoCachingImpl extends BaseSetCaching<Long, User> implements IUserInfoCaching {
    private final IUserRepository userRepository;

    protected UserInfoCachingImpl(JedisConfigCommon jedisConfigCommon,
                                  IUserRepository userRepository) {
        super(jedisConfigCommon);
        this.userRepository = userRepository;
    }

    @Override
    protected Single<Optional<User>> loadData(Long userId) {
        return userRepository.findById(userId);
    }
}
