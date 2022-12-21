package vn.noron.api.service.room;

import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.commons.repository.room.IRoomRepository;
import vn.noron.data.mapper.room.RoomMapper;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.utils.CollectionUtils.collectToMap;
import static vn.noron.utils.CollectionUtils.extractField;

@Service
public class RoomServiceImpl implements IRoomService {
    private final IRoomRepository roomRepository;
    private final IUserService userService;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(IRoomRepository roomRepository, IUserService userService, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.userService = userService;
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
//                    if (!request.getIsAdmin() && !request.getUserId().equals(room.getUserId()))
//                        throw new ApiException("You are not owner this room or admin");
                    roomRepository.updateRoom(roomMapper.toPOJO(request));
                    return "success";
                });

    }
    @Override
    public Single<String> censorshipRoom(String id) {
        roomRepository.updatePendingRoom(id);
        return Single.just("success");

    }

    @Override
    public Single<RoomResponse> getByID(String id) {
        return rxSchedulerIo(() -> roomRepository.getById(id))
                .flatMap(room -> {
                    if (room == null)
                        return Single.error(new ApiException("Room with id " + id + " not exist!"));
                    return userService.getDetailUser(room.getUserId())
                            .map(userResponse -> roomMapper.toResponse(room).setOwnerInfo(userResponse));
                });


    }

    @Override
    public Single<List<RoomResponse>> search(SearchRoomRequest request, Pageable pageable) {
        return Single.zip(roomRepository.search(request, pageable),
                        roomRepository.countSearch(request),
                        (rooms, total) -> {
                            pageable.setTotal(total);
                            return rooms;
                        })
                .flatMap(rooms -> mapRoomOwner(rooms));
    }

    @Override
    public Single<List<RoomResponse>> getByIDS(List<String> roomIds) {
        return roomRepository.getByIds(roomIds)
                .map(roomMapper::roomResponses);
    }

    private Single<List<RoomResponse>> mapRoomOwner(List<Room> rooms) {
        List<Long> userIds = extractField(rooms, Room::getUserId);
        return userService.getByIds(userIds)
                .map(userResponses -> mapRoomResponse(rooms, userResponses));
    }

    @Override
    public Single<List<RoomResponse>> getByUserId(PersonalRoomRequest request, Pageable pageable) {
        return roomRepository.getByUserId(request, pageable)
                .flatMap(pair ->{
                    pageable.setTotal(pair.getLeft());
                    return mapRoomOwner(pair.getRight());
                });
    }

    @Override
    public Single<List<RoomResponse>> getAllPendingRoom( Pageable pageable) {
        return roomRepository.getByStatus( pageable)
                .flatMap(pair ->{
                    pageable.setTotal(pair.getLeft());
                    return mapRoomOwner(pair.getRight());
                });
    }

    private List<RoomResponse> mapRoomResponse(List<Room> rooms, List<UserResponse> userResponses){
        if(rooms.isEmpty()) return new ArrayList<>();
        Map<Long, UserResponse> userResponseMap = collectToMap(userResponses, UserResponse::getId);
        return  roomMapper.roomResponses(rooms)
                .stream()
                .map(r -> r.setOwnerInfo(userResponseMap.getOrDefault(r.getUserId(), new UserResponse())))
                .collect(Collectors.toList());
    }


}
