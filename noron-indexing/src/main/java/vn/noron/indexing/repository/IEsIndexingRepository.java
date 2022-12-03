package vn.noron.indexing.repository;

import co.elastic.clients.elasticsearch.core.DeleteResponse;
import io.reactivex.rxjava3.core.Single;
import vn.noron.data.TDocument;

import java.util.List;

public interface IEsIndexingRepository<T extends TDocument> {
    Single<T> index(T doc);

    Single<List<T>> indexMany(List<T> docs);

    Single<DeleteResponse> delete(String id);
}
