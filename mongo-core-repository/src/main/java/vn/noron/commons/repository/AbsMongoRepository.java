package vn.noron.commons.repository;

import com.google.common.collect.Lists;
import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.noron.core.json.JsonObject;
import vn.noron.data.model.Filter;
import vn.noron.data.model.SearchRequest;
import vn.noron.data.request.room.SearchRoomRequest;
import vn.noron.repository.utils.MongoQueryUtil;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static vn.noron.commons.utils.ObjTransformUtils.objToDocument;
import static vn.noron.commons.utils.ObjTransformUtils.toDocUpdate;
import static vn.noron.data.constant.GenericFieldConstant.*;
import static vn.noron.repository.utils.MongoQueryUtil.buildFilterQueries;
import static vn.noron.utils.CommonUtils.invokeMethodByName;

public abstract class AbsMongoRepository<T> implements IMongoRepository<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final MongoCollection<Document> mongoCollection;
    protected Class<T> tClazz;

    public AbsMongoRepository(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
        try {
            this.tClazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        } catch (Exception exception) {

        }
    }

    @Override
    public void saveMany(List<T> tList) {

        final List<Document> documents = objToDocument(tList);
        Lists.partition(documents, 5000).forEach(mongoCollection::insertMany);
    }

    @Override
    public void updateOnInsert(List<T> tList) {
        final List<UpdateOneModel<Document>> updateOneModels = tList.stream().map(t -> {
            final Object getId = invokeMethodByName(t, "getId");
            if (getId == null) return null;
            return new UpdateOneModel<Document>(eq(_ID, getId), combine(toDocUpdate(t, _ID)), new UpdateOptions().upsert(true));
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (updateOneModels.isEmpty()) return;
        Lists.partition(updateOneModels, 5000).forEach(mongoCollection::bulkWrite);
    }

    @Override
    public void updateOnInsert(List<T> tList, Function<T, Bson> filter) {
        final List<UpdateOneModel<Document>> updateOneModels = tList.stream().map(t -> new UpdateOneModel<Document>(filter.apply(t), combine(toDocUpdate(t)), new UpdateOptions().upsert(true))).collect(Collectors.toList());
        if (updateOneModels.isEmpty()) return;
        Lists.partition(updateOneModels, 5000).forEach(mongoCollection::bulkWrite);
    }

    @Override
    public void updateOnInsert(T t, Function<T, Bson> filter) {
        mongoCollection.updateOne(filter.apply(t), combine(toDocUpdate(t)), new UpdateOptions().upsert(true));
    }

    @Override
    public void saveMany(List<T> tList, Function<T, Bson> filter, Function<T, Boolean> upsert) {
        final List<UpdateOneModel<Document>> updateOneModels = tList.stream().map(t -> new UpdateOneModel<Document>(filter.apply(t), combine(toDocUpdate(t)), new UpdateOptions().upsert(upsert.apply(t)))).collect(Collectors.toList());
        if (updateOneModels.isEmpty()) return;
        Lists.partition(updateOneModels, 5000).forEach(mongoCollection::bulkWrite);
    }

    @Override
    public void updateMany(List<T> tList, Function<T, Bson> filter) {
        final List<UpdateOneModel<Document>> updateOneModels = tList.stream().map(t -> new UpdateOneModel<Document>(filter.apply(t), combine(toDocUpdate(t, _ID)), new UpdateOptions())).collect(Collectors.toList());
        if (updateOneModels.isEmpty()) return;
        Lists.partition(updateOneModels, 5000).forEach(list -> {
            logger.info("[MONGO-SAVE-MANY] class: {} ,size: {}", tClazz.getSimpleName(), list.size());
            mongoCollection.bulkWrite(list);
        });
    }

    @Override
    public void updateMany(List<T> tList, Function<T, Bson> filter, Function<T, Bson> update) {
        final List<UpdateOneModel<Document>> updateOneModels = tList.stream().map(t -> new UpdateOneModel<Document>(filter.apply(t), update.apply(t))).collect(Collectors.toList());
        if (updateOneModels.isEmpty()) return;
        Lists.partition(updateOneModels, 5000).forEach(mongoCollection::bulkWrite);
    }

    @Override
    public T save(T t) {
        mongoCollection.insertOne(objToDocument(t));
        return t;
    }

    @Override
    public T update(Object id, T t) {
        mongoCollection.updateOne(Filters.eq(_ID, id), combine(toDocUpdate(t)));
        return t;
    }

    public T getById(String id) {
        return Optional.ofNullable(mongoCollection.
                find(eq(_ID, id))
                .map(document -> new JsonObject(document).mapTo(tClazz)).first()).orElse(null);
    }

    @Override
    public void delete(String id) {

    }

    public List<T> getByIds(Collection<String> id) {
        return mongoCollection.find(in(_ID, id)).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    @Override
    public Long countAll() {
        return mongoCollection.countDocuments(filterActive());
    }

    @Override
    public List<T> getActive() {
        return mongoCollection.find(filterActive()).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    @Override
    public List<T> getByRangeTime(Long fromTime, Long toTime) {
        return mongoCollection.find(and(filterActive(), gte(getFieldRangeTime(), fromTime), lte(getFieldRangeTime(), toTime))).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    @Override
    public List<T> search(SearchRequest searchRequest) {
        return mongoCollection.find(and(buildFilterQueries(searchRequest.getFilters(), this.tClazz), filterActive(), buildSearchQueries(searchRequest))).sort(MongoQueryUtil.sort(searchRequest.getSorts())).skip(searchRequest.getOffset()).limit(searchRequest.getPageSize()).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    @Override
    public List<T> filters(List<Filter> filters) {
        return mongoCollection.find(and(buildFilterQueries(filters, this.tClazz), filterActive())).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    @Override
    public long count(SearchRequest searchRequest) {
        return mongoCollection.countDocuments(and(buildFilterQueries(searchRequest.getFilters(), this.tClazz), filterActive()));
    }

    protected Bson filterActive() {
        return Filters.exists(_ID, true);
    }
    protected Bson filterPending(Boolean isPending) {
        return eq(PENDING, isPending);
    }

    protected String getFieldRangeTime() {
        return CREATION_TIME;
    }

    protected List<T> execute(List<Bson> bsons) {
        return mongoCollection.find(and(bsons)).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    protected List<T> execute(List<Bson> bsons, Integer limit) {
        return mongoCollection.find(and(bsons)).limit(limit).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    protected List<T> execute(Bson query, Bson sort, Integer limit) {
        return mongoCollection.find(query).sort(sort).limit(limit).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    protected List<T> execute(Bson... bsons) {
        return mongoCollection.find(and(bsons)).map(document -> new JsonObject(document).mapTo(tClazz)).into(new ArrayList<>());
    }

    protected T getFirst(Bson... bsons) {
        return mongoCollection.find(and(bsons)).map(document -> new JsonObject(document).mapTo(tClazz)).first();
    }

    protected Long count(Bson... bsons) {
        return mongoCollection.countDocuments(and(bsons));
    }

    protected Long count(List<Bson> bsons) {
        return mongoCollection.countDocuments(and(bsons));
    }

    protected Bson buildSearchQueries(SearchRequest searchRequest) {
        return Filters.exists(_ID, true);
    }


    protected Bson buildSearchQueries(String keyword) {
        return Filters.exists(_ID, true);
    }

    protected Bson buildSearchQueriesFilter(SearchRoomRequest request) {
        List<Bson> bsons = new ArrayList<>();
        if (request.getRoomLocation() != null) bsons.add(eq("room_location", request.getRoomLocation()));
        if (request.getSearch() != null
                && request.getSearch().getQuery() != null
                && request.getSearch().getQuery().getRoomLocationDistrict() != null)
            bsons.add(eq("room_location_district", request.getSearch().getQuery().getRoomLocationDistrict()));
        if(request.getFilter() != null){
            if (request.getFilter().getPrice() != null)
                bsons.add(and(gt("room_price", request.getFilter().getPrice().getFrom()),
                        lt("room_price", request.getFilter().getPrice().getTo())));
            if (request.getFilter().getRoomGender() != null)
                bsons.add(in("room_gender", request.getFilter().getRoomGender()));
            if (request.getFilter().getRoomType() != null)
                bsons.add(in("room_type", request.getFilter().getRoomType()));
            if (request.getFilter().getUtilities() != null)
                request.getFilter().getUtilities().stream()
                        .forEach(ultility -> {
                            bsons.add(eq(ultility, true));
                        });
        }
        return and(bsons);

    }
}
