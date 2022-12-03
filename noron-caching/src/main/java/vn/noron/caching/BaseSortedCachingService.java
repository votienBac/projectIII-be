package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;
import redis.clients.jedis.Tuple;
import vn.noron.caching.config.redis.JedisConfigCommon;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.core.template.RxTemplate.rxSubscribe;

public abstract class BaseSortedCachingService<ID, V> extends BaseRedis<ID> implements ISortedCaching<ID, V> {

    protected BaseSortedCachingService(JedisConfigCommon jedisConfigCommon) {
        super(jedisConfigCommon);
    }

    protected boolean isExitedMember(ID id, String member) {
        return getRedis().zscore(getRedisKey(id), member) != null;
    }

    @Override
    public Single<V> getMember(ID id, String member) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ?
                        rxSchedulerIo(() -> getRedis().zscore(getRedisKey(id), member)) :
                        loadDataToRedis(id).map(map -> map.get(member)))
                .map(zscore -> toObject(id, member, zscore));
    }

    @Override
    public Single<List<V>> getByIdDesc(ID id, Integer limit) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ? getObjectDesc(id, limit) : loadDataDescToObject(id, limit));
    }

    private Single<List<V>> getObjectDesc(ID id, Integer limit) {
        return rxSchedulerIo(() -> getRedis().zrevrangeByScoreWithScores(getRedisKey(id), MAX_VALUE, MIN_VALUE, 0, limit))
                .map(tuples -> toListObject(id, tuples));
    }

    private Single<List<V>> loadDataDescToObject(ID id, Integer limit) {
        return loadDataToRedis(id).map(map -> map.entrySet()
                .stream().sorted(comparingByValue(reverseOrder()))
                .limit(limit)
                .collect(Collectors.toList()))
                .map(entries -> toListObject(id, entries));
    }

    @Override
    public Single<List<V>> getByValue(ID id, Double minValue, Double maxValue) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ? Single.just(new HashMap<>()) : loadDataToRedis(id))
                .flatMap(map -> rxSchedulerIo(
                        () -> getRedis().zrangeByScoreWithScores(getRedisKey(id), minValue, maxValue)))
                .map(tuples -> toListObject(id, tuples));
    }

    @Override
    public Single<List<V>> getByValue(ID id, Double minValue, Double maxValue, Integer limit) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ? Single.just(new HashMap<>()) : loadDataToRedis(id))
                .flatMap(map -> rxSchedulerIo(
                        () -> getRedis().zrangeByScoreWithScores(getRedisKey(id), minValue, maxValue, 0, limit)))
                .map(tuples -> toListObject(id, tuples));
    }

    @Override
    public Single<List<V>> getAllValues(ID id) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ? getAllMember(id) : loadDataToRedis(id))
                .map(memberValueMap -> toObjects(id, memberValueMap));
    }

    @Override
    public Single<Double> getMemberValue(ID id, String member) {
        final String redisKey = getRedisKey(id);
        return isExitedKey(redisKey)
                .flatMap(isExited -> isExited ?
                        rxSchedulerIo(() -> getRedis().zscore(redisKey, member)) :
                        loadDataToRedis(id).map(map -> map.get(member)));
    }

    @Override
    public Single<List<String>> removeMembers(ID id, List<String> members) {
        return rxSchedulerIo(() -> {
            if (!members.isEmpty()) getRedis().zrem(getRedisKey(id), members.toArray(new String[0]));
            return members;
        });
    }

    protected Single<Map<String, Double>> getAllMember(ID id) {
        return rxSchedulerIo(() -> {
            Set<Tuple> tuples = getRedis().zrangeByScoreWithScores(getRedisKey(id), 0, MAX_VALUE);
            if (tuples == null) return new HashMap<>();
            return tuples.stream().collect(toMap(Tuple::getElement, Tuple::getScore));
        });
    }

    @Override
    public void reloadByKey(ID id) {
        final String redisKey = getRedisKey(id);
        rxSubscribe(rxSchedulerIo(() -> getRedis().del(redisKey))
                .flatMap(aLong -> loadDataToRedis(id)));
    }

    private List<V> toListObject(ID id, Set<Tuple> tuples) {
        if (tuples == null) return new ArrayList<>();
        return tuples.stream()
                .map(tuple -> toObject(id, tuple.getElement(), tuple.getScore()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<V> toListObject(ID id, Collection<Map.Entry<String, Double>> entries) {
        if (entries == null || entries.isEmpty()) return new ArrayList<>();
        return entries.stream()
                .map(tuple -> toObject(id, tuple.getKey(), tuple.getValue()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected abstract V toObject(ID id, String member, Double value);

    protected Single<V> saveMember(ID id, String member, Double value) {
        return rxSchedulerIo(() -> {
            try {
                getRedis().zadd(getRedisKey(id), value, member);
                getRedis().expire(getRedisKey(id), cacheConfig.expireSecond());
            } catch (Exception exception) {
                logger.error("[REDIS] fail to save member:  {} value: {} to redis-sorted ", member, value, exception);
            }
            return toObject(id, member, value);
        });
    }

    private List<V> toObjects(ID id, Map<String, Double> memberValueMap) {
        return memberValueMap
                .entrySet()
                .stream()
                .map(entry -> toObject(id, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Single<Map<String, Double>> loadDataToRedis(ID id) {
        return loadData(id)
                .flatMap(valueMap -> saveMap(id, valueMap));
    }

    protected Single<Map<String, Double>> saveMap(ID id, Map<String, Double> memberValuesMap) {
        return rxSchedulerIo(() -> {
            try {
                getRedis().zadd(getRedisKey(id), memberValuesMap);
                getRedis().expire(getRedisKey(id), cacheConfig.expireSecond());
            } catch (Exception exception) {
                logger.error("[REDIS] fail to save to redis-sorted ", exception);
            }
            return memberValuesMap;
        });
    }

    protected abstract Single<Map<String, Double>> loadData(ID id);
}
