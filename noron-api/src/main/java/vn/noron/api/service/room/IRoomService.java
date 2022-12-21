package vn.noron.api.service.room;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.model.SearchRequest;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;
import vn.noron.data.response.room.RoomResponse;

import java.util.List;

public interface IRoomService {

    Single<Room> createRoom(CreateRoomRequest request);
    Single<String> updateRoom(UpdateRoomRequest request);

    Single<String> censorshipRoom(String id);

    Single<RoomResponse> getByID(String id);
    Single<List<RoomResponse>>  search(SearchRoomRequest request, Pageable pageable);
    Single<List<RoomResponse>>  getByUserId(PersonalRoomRequest request, Pageable pageable);
    Single<List<RoomResponse>> getAllPendingRoom( Pageable pageable);
}
