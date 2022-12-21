package vn.noron.api.service.favoriteroom;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.favoriteroom.FavoriteRoomRequest;
import vn.noron.data.response.room.RoomResponse;

import java.util.List;

public interface IFavoriteRoomService {
    Single<String> insert(FavoriteRoomRequest request);
    Single<String> delete(FavoriteRoomRequest request);
    Single<List<RoomResponse>> getAllByUserId(Long userId, Pageable pageable);
}
