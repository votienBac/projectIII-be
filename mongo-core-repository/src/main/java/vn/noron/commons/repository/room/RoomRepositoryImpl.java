package vn.noron.commons.repository.room;

import com.mongodb.client.MongoCollection;
import vn.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import vn.noron.core.json.JsonObject;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.repository.utils.MongoQueryUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static vn.noron.commons.utils.ObjTransformUtils.toDocUpdate;
import static vn.noron.commons.utils.ObjTransformUtils.toDocumentInsert;
import static vn.noron.data.constant.GenericFieldConstant.*;
import static vn.noron.repository.utils.MongoQueryUtil.buildFilterQueries;

@Repository
public class RoomRepositoryImpl extends AbsMongoRepository<Room> implements IRoomRepository {
    public RoomRepositoryImpl(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);
    }

//    @Override
//    public Room createRoom(Room room) {
//        try {
//            mongoCollection.insertOne(toDocumentInsert(room));
//            return room;
//        } catch (Exception e) {
//            logger.error("Can not save room {}, {}", room, e.getCause());
//            return null;
//        }
//    }

    @Override
    public void updateRoom(Room room) {
        mongoCollection.updateOne(
                eq(_ID, room.getId()),
                combine(toDocUpdate(room, _ID)));
    }

    @Override
    public List<Room> getByIds(List<String> id, String roomType) {
        return mongoCollection.find(and(in(_ID, id), eq(ROOM_TYPE, roomType)))
                .map(document -> new JsonObject(document).mapTo(Room.class))
                .into(new ArrayList<>());
    }
    @Override
    public List<Room> getRoomByStatus(Boolean isPending){
        return mongoCollection.find(eq(PENDING, isPending))
                .map(document -> new JsonObject(document).mapTo(Room.class))
                .into(new ArrayList<>());
    }

    @Override
    public List<Room> searchPageable(String keyword, Pageable pageable) {
        return mongoCollection
                .find(and(
                        //buildFilterQueries(pageable.(), this.tClazz),
                        filterActive(),
                        buildSearchQueries(keyword)))
                .sort(MongoQueryUtil.sort(pageable.getSort()))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(document -> new vn.noron.core.json.JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>());
    }

    @Override
    public long countSearchPageable(String keyword) {
        return mongoCollection
                .find(and(
                        //buildFilterQueries(pageable.(), this.tClazz),
                        filterActive(),
                        buildSearchQueries(keyword)))
                .map(document -> new JsonObject(document).mapTo(tClazz))
                .into(new ArrayList<>())
                .stream().count();
    }
}
