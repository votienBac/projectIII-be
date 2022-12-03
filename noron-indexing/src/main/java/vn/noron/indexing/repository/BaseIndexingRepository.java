package vn.noron.indexing.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import io.reactivex.rxjava3.core.Single;
import vn.noron.config.anotation.Index;
import vn.noron.data.TDocument;

import java.util.List;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;

public abstract class BaseIndexingRepository<T extends TDocument> implements IEsIndexingRepository<T> {
    protected final ElasticsearchClient client;
    private String index;

    protected BaseIndexingRepository(ElasticsearchClient client) {
        this.client = client;
        this.index = this.getClass().getAnnotation(Index.class).indexName();
    }

    @Override
    public Single<T> index(T doc) {
        return rxSchedulerIo(() -> {
            client.index(builder -> builder
                    .index(index)
                    .id(doc.getId())
                    .document(doc));
            return doc;
        });
    }

    @Override
    public Single<List<T>> indexMany(List<T> docs) {
        return rxSchedulerIo(() -> {
            BulkRequest.Builder request = new BulkRequest.Builder();
            docs.forEach(t -> request.operations(builder -> builder
                    .index(objectBuilder -> objectBuilder.index(index).id(t.getId()).document(t))));
            client.bulk(request.build());
            return docs;
        });
    }

    @Override
    public Single<DeleteResponse> delete(String id) {
        return rxSchedulerIo(() -> client.delete(builder -> builder.index(index).id(id)));
    }
}
