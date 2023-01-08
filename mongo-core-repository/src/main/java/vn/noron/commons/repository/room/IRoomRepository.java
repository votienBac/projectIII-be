package vn.noron.commons.repository.room;

import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import vn.noron.commons.repository.IMongoRepository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;

import java.util.List;

public interface IRoomRepository extends IMongoRepository<Room> {
    //Room createRoom(Room room);

    void updateRoom(Room room);
    Single<List<Room>> getByIds(List<String> id);

    void updatePendingRoom(String id);

    Single<List<Room>> search(SearchRoomRequest request, Pageable pageable);

    List<Room> getAll();

    Single<Long> countSearch(SearchRoomRequest request);
    Single<Pair<Long, List<Room>>> getByUserId(PersonalRoomRequest request, Pageable pageable);
    Single<Pair<Long, List<Room>>> getByStatus(Pageable pageable);
}
