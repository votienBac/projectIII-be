package vn.noron.api.service.room;

import io.reactivex.rxjava3.core.Single;
import org.springframework.security.core.Authentication;
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
    Single<String> deleteRoom(String id, Authentication authentication);

    Single<RoomResponse> roomDetail(String id, Long userId);
    Single<List<RoomResponse>>  search(SearchRoomRequest request, Pageable pageable);

    Single<List<RoomResponse>> getByIDS(List<String> roomIds);

    Single<List<RoomResponse>>  getByUserId(PersonalRoomRequest request, Pageable pageable);
    Single<List<RoomResponse>> getAllPendingRoom( Pageable pageable);
}
