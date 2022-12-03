package vn.noron.searching.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;
import io.reactivex.rxjava3.core.Single;
import vn.noron.config.anotation.Index;
import vn.noron.data.TDocument;
import vn.noron.data.request.EsSearchRequest;
import vn.noron.data.response.EsSearchResponse;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;

public abstract class BaseSearchingRepository<T extends TDocument> implements IEsSearchingRepository<T> {
    protected final ElasticsearchClient client;
    private String index;
    private Class<T> tClass;

    protected BaseSearchingRepository(ElasticsearchClient client) {
        this.client = client;
        this.tClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        this.index = this.getClass().getAnnotation(Index.class).indexName();
    }

    @Override
    public Single<Optional<T>> findById(String id) {
        return rxSchedulerIo(() -> {
            GetResponse<T> response = client.get(builder -> builder.index(index).id(id), tClass);
            if (response.found()) return Optional.ofNullable(response.source());
            return Optional.empty();
        });
    }

    @Override
    public Single<EsSearchResponse<T>> search(EsSearchRequest searchRequest) {
        return rxSchedulerIo(() -> {
            SearchResponse<T> response = client.search(
                    builder -> builder
                            .index(index)
                            .query(toQueryBuilder(searchRequest)), tClass);
            final List<T> items = response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());
            assert response.hits().total() != null;
            return new EsSearchResponse<T>()
                    .setItems(items)
                    .setScrollId(response.scrollId())
                    .setTotal(response.hits().total().value());
        });
    }

    protected abstract Function<Query.Builder, ObjectBuilder<Query>> toQueryBuilder(EsSearchRequest request);

    @Override
    public Single<List<T>> scroll(String scrollId) {
        return null;
    }
}
