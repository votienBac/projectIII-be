package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;

public interface ICounterCaching<ID> {
    Single<Double> getByKey(ID id);

    Single<Boolean> isExitedKey(String redisKey);

    Single<Long> incr(ID id);

    Single<Long> incrBy(ID id, long value);

    Single<Double> incrBy(ID id, double value);

    Single<Long> decr(ID id);

    Single<Long> decrBy(ID id, long value);

    void removeById(ID id);
}
