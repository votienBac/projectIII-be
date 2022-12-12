package vn.noron.api.service.room;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.model.SearchRequest;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;

import java.util.List;

public interface IRoomService {

    Single<Room> createRoom(CreateRoomRequest request);
    Single<String> updateRoom(UpdateRoomRequest request);
    Single<Room> getByID(String id);
    Single<List<Room>> search(String keyword, Pageable pageable);
}
