package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;
import vn.noron.caching.config.redis.JedisConfigCommon;

import static org.apache.commons.lang3.math.NumberUtils.createDouble;
import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.core.template.RxTemplate.rxSchedulerNewThreadSubscribe;

public abstract class BaseCounterCaching<ID> extends BaseRedis<ID> implements ICounterCaching<ID> {

    protected BaseCounterCaching(JedisConfigCommon jedisConfigCommon) {
        super(jedisConfigCommon);
    }

    @Override
    public Single<Double> getByKey(ID id) {
        return loadDataIfNotExitedKey(id)
                .flatMap(isLoadData -> rxSchedulerIo(() -> {
                    final String value = getRedis().get(getRedisKey(id));
                    if (value == null) return 0.0;
                    return createDouble(value);
                }));
    }

    @Override
    public Single<Long> incr(ID id) {
        return loadDataIfNotExitedKey(id)
                .flatMap(isLoadData -> rxSchedulerIo(() -> getRedis().incr(getRedisKey(id))));
    }

    @Override
    public Single<Long> incrBy(ID id, long value) {
        return loadDataIfNotExitedKey(id)
                .flatMap(isLoadData -> rxSchedulerIo(() -> getRedis().incrBy(getRedisKey(id), value)));
    }

    @Override
    public Single<Double> incrBy(ID id, double value) {
        return loadDataIfNotExitedKey(id)
                .flatMap(isLoadData -> rxSchedulerIo(() -> getRedis().incrByFloat(getRedisKey(id), value)));
    }

    @Override
    public Single<Long> decr(ID id) {
        return loadDataIfNotExitedKey(id)
                .flatMap(isLoadData -> rxSchedulerIo(() -> getRedis().decr(getRedisKey(id))));
    }

    @Override
    public Single<Long> decrBy(ID id, long value) {
        return loadDataIfNotExitedKey(id)
                .flatMap(isLoadData -> rxSchedulerIo(() -> getRedis().decrBy(getRedisKey(id), value)));
    }

    private Single<Double> saveToRedis(ID id, Double value) {
        return rxSchedulerIo(() -> {
            try {
                getRedis().incrByFloat(getRedisKey(id), value);
                getRedis().expire(getRedisKey(id), cacheConfig.expireSecond());
            } catch (Exception exception) {
                logger.error("[REDIS] fail to save to redis-counter ", exception);
            }
            return value;
        });
    }

    public Single<Boolean> loadDataIfNotExitedKey(ID id) {
        return isExitedKey(getRedisKey(id))
                .flatMap(oldValue -> {
                    if (oldValue == null)
                        return loadDataToCache(id).map(aDouble -> true);
                    return Single.just(false);
                });
    }

    protected Single<Double> loadDataToCache(ID id) {
        return loadData(id).flatMap(value -> saveToRedis(id, value));
    }

    protected abstract Single<Double> loadData(ID id);

    @Override
    public void removeById(ID id) {
        rxSchedulerNewThreadSubscribe(() -> getRedis().del(getRedisKey(id)));
    }
}
