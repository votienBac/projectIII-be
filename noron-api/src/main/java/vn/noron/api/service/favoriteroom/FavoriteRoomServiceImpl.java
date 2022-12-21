package vn.noron.api.service.favoriteroom;

import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.api.service.room.IRoomService;
import vn.noron.data.mapper.favoriteroom.FavoriteRoomMapper;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.favoriteroom.FavoriteRoomRequest;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.repository.favoriteroom.IFavoriteRoomRepository;
import vn.noron.utils.CollectionUtils;

import java.util.List;

import static vn.noron.utils.CollectionUtils.extractField;

@Service
public class FavoriteRoomServiceImpl implements IFavoriteRoomService{
    private final IFavoriteRoomRepository favoriteRoomRepository;
    private final FavoriteRoomMapper favoriteRoomMapper;
    private final IRoomService roomService;

    public FavoriteRoomServiceImpl(IFavoriteRoomRepository favoriteRoomRepository,
                                   FavoriteRoomMapper favoriteRoomMapper,
                                   IRoomService roomService) {
        this.favoriteRoomRepository = favoriteRoomRepository;
        this.favoriteRoomMapper = favoriteRoomMapper;
        this.roomService = roomService;
    }

    @Override
    public Single<String> insert(FavoriteRoomRequest request) {
        return favoriteRoomRepository.insert(favoriteRoomMapper.toPOJO(request))
                .map(integer -> "success");
    }

    @Override
    public Single<String> delete(FavoriteRoomRequest request) {
        return favoriteRoomRepository.deleteFromFavorite(request.getUserId(), request.getRoomId())
                .map(integer -> "success");
    }

    @Override
    public Single<List<RoomResponse>> getAllByUserId(Long userId, Pageable pageable) {
        return favoriteRoomRepository.getPageable(userId, pageable)
                .flatMap(pair -> {
                    pageable.setTotal(pair.getLeft());
                    List<String> roomIds = extractField(pair.getRight(), FavoriteRoom::getRoomId);
                    return roomService.getByIDS(roomIds);
                });
    }
}
