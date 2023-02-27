package vn.noron.commons.repository.room;

import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import vn.noron.commons.repository.IMongoRepository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;

import java.util.List;
import java.util.Map;

public interface IRoomRepository extends IMongoRepository<Room> {
    //Room createRoom(Room room);

    void updateRoom(Room room);
    Single<List<Room>> getByIds(List<String> id);

    void updatePendingRoom(String id);
    void deleteRoom(String id);

    String updateStatus(List<String> ids, Boolean isDisabled);

    Single<List<Room>> search(SearchRoomRequest request, Pageable pageable);

    Map<Long, Integer> getNumberRoomOfUsers(List<Long> userIds);

    Single<List<Room>>  getByUserIds(List<Long> userIds);

    Single<List<Room>> getByUserId(Long userId);

    List<Room> getAll();

    Single<Long> countSearch(SearchRoomRequest request);
    Single<Pair<Long, List<Room>>> getByUserId(PersonalRoomRequest request, Pageable pageable);
    Single<Pair<Long, List<Room>>> getByStatus(Pageable pageable);
}
