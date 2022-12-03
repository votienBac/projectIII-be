package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;
import vn.noron.caching.config.redis.JedisConfigCommon;
import vn.noron.core.exception.DBException;
import vn.noron.core.json.JsonObject;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import static vn.noron.core.template.RxTemplate.*;

public abstract class BaseSetCaching<ID, V> extends BaseRedis<ID> implements ISetCaching<ID, V> {
    private final Class<V> valueClass;

    protected BaseSetCaching(JedisConfigCommon jedisConfigCommon) {
        super(jedisConfigCommon);
        this.valueClass = ((Class<V>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    public Single<Optional<V>> getByIdOptional(ID id) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ? getOrElseLoadData(id) : loadAndSaveData(id));
    }

    public Single<V> getById(ID id) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ? getOrElseLoadData(id) : loadAndSaveData(id))
                .flatMap(v -> v.map(Single::just).orElse(Single.error(new DBException("resource not found"))));
    }

    private Single<Optional<V>> getOrElseLoadData(ID id) {
        return getObjectFromRedis(id)
                .flatMap(value -> value.isEmpty() ? loadAndSaveData(id) : Single.just(value));
    }

    private Single<Optional<V>> getObjectFromRedis(ID id) {
        return rxSchedulerIo(() -> {
            try {
                final String value = getRedis().get(getRedisKey(id));
                if (value == null) return Optional.<V>empty();
                return Optional.ofNullable(new JsonObject(value).mapTo(valueClass));
            } catch (Exception e) {
                logger.error("[REDIS] fetch data fail cause: ", e);
                return Optional.<V>empty();
            }
        });
    }

    @Override
    public void reloadById(ID id) {
        final String redisKey = getRedisKey(id);
        rxSubscribe(rxSchedulerIo(() -> getRedis().del(redisKey))
                .flatMap(aLong -> loadAndSaveData(id)));
    }

    private Single<Optional<V>> loadAndSaveData(ID id) {
        final String redisKey = getRedisKey(id);
        return loadData(id)
                .flatMap(optionalV -> optionalV
                        .map(v -> saveToRedis(redisKey, v).map(Optional::ofNullable))
                        .orElse(Single.just(Optional.empty())));
    }

    private Single<V> saveToRedis(String redisKey, V value) {
        return rxSchedulerIo(() -> {
            try {
                getRedis().set(redisKey, JsonObject.mapFrom(value).encode());
                getRedis().expire(redisKey, cacheConfig.expireSecond());
            } catch (Exception exception) {
                logger.error("[REDIS] fail to save to redis-set ", exception);
            }
            return value;
        });
    }

    @Override
    public void deleteById(ID id) {
        rxSchedulerNewThreadSubscribe(() -> getRedis().del(getRedisKey(id)));
    }

    protected abstract Single<Optional<V>> loadData(ID id);
}
