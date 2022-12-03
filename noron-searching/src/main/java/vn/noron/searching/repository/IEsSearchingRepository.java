package vn.noron.searching.repository;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.TDocument;
import vn.noron.data.request.EsSearchRequest;
import vn.noron.data.response.EsSearchResponse;

import java.util.List;
import java.util.Optional;

public interface IEsSearchingRepository<T extends TDocument> {
    Single<Optional<T>> findById(String id);

    Single<EsSearchResponse<T>> search(EsSearchRequest searchRequest);

    Single<List<T>> scroll(String scrollId);
}
