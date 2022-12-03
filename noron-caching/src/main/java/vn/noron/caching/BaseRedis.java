package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;
import redis.clients.jedis.JedisCluster;
import vn.noron.caching.config.annotation.CacheConfig;
import vn.noron.caching.config.redis.JedisConfigCommon;
import vn.noron.core.log.Logger;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;

public abstract class BaseRedis<ID> {
    protected final Logger logger = Logger.getLogger(this.getClass());
    protected final JedisConfigCommon jedisConfigCommon;
    protected final CacheConfig cacheConfig;

    protected BaseRedis(JedisConfigCommon jedisConfigCommon) {
        this.jedisConfigCommon = jedisConfigCommon;
        this.cacheConfig = this.getClass().getAnnotation(CacheConfig.class);
    }

    protected String getRedisKey(ID id) {
        return String.format(this.cacheConfig.pattern(), id);
    }

    protected JedisCluster getRedis() {
        return jedisConfigCommon.getRedisCluster();
    }

    public Single<Boolean> isExitedKey(String redisKey) {
        return rxSchedulerIo(() -> {
            final Boolean existed = getRedis().exists(redisKey);
            return existed != null && existed;
        });
    }
}
