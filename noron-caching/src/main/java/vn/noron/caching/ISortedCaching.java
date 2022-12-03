package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface ISortedCaching<ID, V> {
    Single<V> getMember(ID id, String member);

    Single<List<V>> getByIdDesc(ID id, Integer limit);

    Single<List<V>> getByValue(ID id, Double minValue, Double maxValue);

    Single<List<V>> getByValue(ID id, Double minValue, Double maxValue, Integer limit);

    Single<List<V>> getAllValues(ID id);

    Single<Double> getMemberValue(ID id, String member);

    Single<List<String>> removeMembers(ID id, List<String> members);

    void reloadByKey(ID id);
}
