package vn.noron.commons.repository.room;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import vn.noron.commons.repository.AbsMongoRepository;
import vn.noron.core.json.JsonObject;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;
import vn.noron.repository.utils.MongoQueryUtil;

import java.util.*;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static java.util.stream.Collectors.toMap;
import static vn.noron.commons.utils.ObjTransformUtils.toDocUpdate;
import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.constant.GenericFieldConstant.*;
import static vn.noron.utils.CollectionUtils.extractField;

@Repository
public class RoomRepositoryImpl extends AbsMongoRepository<Room> implements IRoomRepository {
    public RoomRepositoryImpl(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);
    }

    @Override
    public void updateRoom(Room room) {
        mongoCollection.updateOne(
                eq(_ID, room.getId()),
                combine(toDocUpdate(room, _ID)));
    }

    @Override
    public Single<List<Room>> getByIds(List<String> id) {
        return rxSchedulerIo(() -> {
            List<Room> rooms = mongoCollection
                    .find(and(in(_ID, id), eq(PENDING, false)))
                    .map(document -> new JsonObject(document).mapTo(tClazz))
                    .into(new ArrayList<>());
            return rooms;
        });
    }

    @Override
    public void updatePendingRoom(String id) {
        mongoCollection.updateOne(
                eq(_ID, id),
                set(PENDING, false));
    }


    @Override
    public Single<List<Room>> search(SearchRoomRequest request, Pageable pageable) {
        return rxSchedulerIo(() -> mongoCollection
                .find(and(filterActive(),
                        buildSearchQueriesFilter(request)))
                .sort(MongoQueryUtil.sort(pageable.getSort()))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>()));
    }

    @Override
    public Map<Long, Integer> getNumberRoomOfUsers(List<Long> userIds) {
//        return mongoCollection.aggregate(
//                        List.of(group("$user_id",  Accumulators.sum("count", 1)),
//                                match(in(USER_ID, userIds))))
//                .into(new ArrayList<>())
//                .stream()
//                .;
        return new HashMap<>();

    }
    @Override
    public Single<List<Room>> getByUserIds(List<Long> userIds){
        return rxSchedulerIo(() -> mongoCollection
                .find(and(filterActive(), in(USER_ID, userIds)))
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>()));
    }

    @Override
    public List<Room> getAll() {
        return mongoCollection.find(filterActive())
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>());
    }

    @Override
    public Single<Long> countSearch(SearchRoomRequest request) {
        return rxSchedulerIo(() -> mongoCollection
                .find(and(
                        filterActive(),
                        filterPending(false),
                        buildSearchQueriesFilter(request)))
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>())
                .stream().count());
    }

    @Override
    public Single<Pair<Long, List<Room>>> getByUserId(PersonalRoomRequest request, Pageable pageable) {
        return rxSchedulerIo(() -> {
            List<Bson> bsons = new ArrayList<>();
            bsons.add(filterActive());
            bsons.add(eq(USER_ID, request.getUserId()));
            if (request.getIsPending() != null)
                bsons.add(filterPending(request.getIsPending()));
            FindIterable<Document> findIterable = mongoCollection
                    .find(and(bsons));
            Long total = findIterable
                    .map(document -> new JsonObject(document).mapTo(tClazz))
                    .into(new ArrayList<>())
                    .stream().count();
            List<Room> rooms = findIterable
                    .sort(MongoQueryUtil.sort(pageable.getSort()))
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .map(document -> new JsonObject(document).mapTo(tClazz))
                    .into(new ArrayList<>());

            return Pair.of(total, rooms);
        });
    }

    @Override
    public Single<Pair<Long, List<Room>>> getByStatus(Pageable pageable) {
        return rxSchedulerIo(() -> {
            List<Bson> bsons = new ArrayList<>();
            bsons.add(filterActive());
            bsons.add(filterPending(true));
            FindIterable<Document> findIterable = mongoCollection
                    .find(and(bsons));
            Long total = findIterable
                    .map(document -> new JsonObject(document).mapTo(tClazz))
                    .into(new ArrayList<>())
                    .stream().count();
            List<Room> rooms = findIterable
                    .sort(MongoQueryUtil.sort(pageable.getSort()))
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .map(document -> new JsonObject(document).mapTo(tClazz))
                    .into(new ArrayList<>());

            return Pair.of(total, rooms);
        });
    }

}
