package vn.noron.api.service.room;

import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.commons.repository.room.IRoomRepository;
import vn.noron.data.mapper.room.RoomMapper;
import vn.noron.data.model.SearchRequest;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {
    private final IRoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(IRoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public Single<Room> createRoom(CreateRoomRequest request) {
        return Single.just(roomRepository.save(roomMapper.toPOJO(request)));
    }

    @Override
    public Single<String> updateRoom(UpdateRoomRequest request) {
        return getByID(request.getId())
                .map(room -> {
                    if (!request.getIsAdmin() && !request.getUserId().equals(room.getUserId()))
                        throw new ApiException("You are not owner this room or admin");
                    roomRepository.updateRoom(roomMapper.toPOJO(request));
                    return "success";
                });

    }
    @Override
    public Single<Room> getByID(String id) {
        Room room = roomRepository.getById(id);
        if (room == null)
            return Single.error(new ApiException("Room with id " + id + " not exist!"));
        return Single.just(room);
    }

    @Override
    public Single<List<Room>> search(String keyword, Pageable pageable) {
        List<Room> rooms = roomRepository.searchPageable(keyword, pageable);
        pageable.setTotal(roomRepository.countSearchPageable(keyword));
        return Single.just(rooms);
    }
}
