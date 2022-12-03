package vn.noron.caching;

import io.reactivex.rxjava3.core.Single;

import java.util.Optional;

public interface ISetCaching<ID, V> {
    Single<Optional<V>> getByIdOptional(ID id);

    Single<V> getById(ID id);

    void reloadById(ID id);

    void deleteById(ID id);
}
