package vn.noron.commons.repository;

import com.mongodb.Function;
import org.bson.conversions.Bson;
import vn.noron.data.model.Filter;
import vn.noron.data.model.SearchRequest;

import java.util.Collection;
import java.util.List;

public interface IMongoRepository<T> {
    void saveMany(List<T> tList);

    void updateOnInsert(List<T> tList);

    void updateOnInsert(List<T> tList, Function<T, Bson> filter);

    void updateOnInsert(T t, Function<T, Bson> filter);

    void saveMany(List<T> tList, Function<T, Bson> filter, Function<T, Boolean> upsert);

    void updateMany(List<T> tList, Function<T, Bson> filter);

    void updateMany(List<T> tList, Function<T, Bson> filter, Function<T, Bson> update);

    T save(T t);

    T update(Object id, T t);

    T getById(String id);

    Long delete(String id);

    List<T> getByIds(Collection<String> ids);

    Long countAll();

    List<T> getActive();



    List<T> getByRangeTime(Long fromTime, Long toTime);

    List<T> search(SearchRequest searchRequest);


    List<T> filters(List<Filter> filters);

    long count(SearchRequest searchRequest);
}
