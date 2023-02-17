package vn.noron.api.service.room;

import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.commons.repository.room.IRoomRepository;
import vn.noron.data.mapper.room.RoomMapper;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.GeoCoding;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.data.tables.pojos.ReportRoom;
import vn.noron.repository.favoriteroom.IFavoriteRoomRepository;
import vn.noron.repository.reportroom.IReportRoomRepository;
import vn.noron.utils.authentication.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static vn.noron.utils.CollectionUtils.*;

@Service
public class RoomServiceImpl implements IRoomService {
    private final IRoomRepository roomRepository;
    private final IFavoriteRoomRepository favoriteRoomRepository;
    private final IReportRoomRepository reportRoomRepository;
    private final IUserService userService;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(IRoomRepository roomRepository,
                           IFavoriteRoomRepository favoriteRoomRepository,
                           IReportRoomRepository reportRoomRepository, IUserService userService,
                           RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.favoriteRoomRepository = favoriteRoomRepository;
        this.reportRoomRepository = reportRoomRepository;
        this.userService = userService;
        this.roomMapper = roomMapper;
    }

    @Override
    public Single<Room> createRoom(CreateRoomRequest request) {
        return Single.just(roomRepository.save(roomMapper.toPOJO(request)));
    }

    @Override
    public Single<String> updateRoom(UpdateRoomRequest request) {
        return roomDetail(request.getId(), null)
                .map(room -> {
                    if (!request.getIsAdmin() && !request.getUserId().equals(room.getUserId()))
                        throw new ApiException("You are not owner this room or admin");
                    roomRepository.updateRoom(roomMapper.toPOJO(request));
                    return "success";
                });

    }

    @Override
    public Single<String> deleteRoom(String roomId) {
        return null;
    }

    @Override
    public void updateIsPaidRoom(String roomId, Boolean isPaid) {
        roomRepository.updateRoom(roomRepository.getById(roomId).setIsPaid(isPaid));

    }

    //@PostConstruct
    //@Scheduled(cron = "0 15 0 0 0 0")
    public void updateLocationForRoom() {
        List<Room> rooms = roomRepository.getAll();
        List<Room> updateRooms = rooms.stream()
                .filter(room -> room.getGeocodingApi() != null)
                .map(room -> room.setLocation(new GeoCoding.Location()
                        .setLng(room.getGeocodingApi().getLocation().getLng())
                        .setLat(room.getGeocodingApi().getLocation().getLat())))
                .collect(Collectors.toList());
        roomRepository.updateOnInsert(updateRooms);

    }

    @Override
    public Single<String> censorshipRoom(String id) {
        roomRepository.updatePendingRoom(id);
        return Single.just("success");

    }

    @Override
    public Single<String> deleteRoom(String id, Authentication authentication) {
        return roomDetail(id, null)
                .map(room -> {
                    if (!AuthenticationUtils.isAdmin(authentication)
                            && !AuthenticationUtils.loggedUserId(authentication).equals(room.getUserId()))
                        throw new ApiException("You are not owner this room or admin");
                    roomRepository.delete(id);
                    return "success";
                });
    }

    @Override
    public Single<RoomResponse> roomDetail(String id, Long userId) {
        Room room = roomRepository.getById(id);
        if (room == null)
            return Single.error(new ApiException("Room with id " + id + " not exist!"));
        return Single.zip(
                userService.getDetailUser(room.getUserId()),
                getFavoriteOfUser(userId),
                (userResponse, favoriteRoomIds) -> {
                    RoomResponse res = roomMapper.toResponse(room).setOwnerInfo(userResponse);
                    if (favoriteRoomIds.contains(res.getId())) return res.setIsFavoriteRoom(true);
                    return res;
                });


    }

    @Override
    public Single<List<RoomResponse>> search(SearchRoomRequest request, Pageable pageable) {
        return Single.zip(roomRepository.search(request, pageable),
                        roomRepository.countSearch(request),
                        getFavoriteOfUser(request.getUserId()),
                        (rooms, total, favoriteRoomIds) -> {
                            pageable.setTotal(total);

                            return Pair.of(rooms, favoriteRoomIds);
                        })
                .flatMap(pair -> Single.zip(mapRoomOwner(pair.getLeft(), pair.getRight()),
                        reportRoomRepository.getAllByRoomIds(extractField(pair.getLeft(), Room::getId)),
                        ((roomResponses, reportRooms) -> {
                            Map<String, Long> mapRoomWithNumberReport = groupCount(reportRooms, reportRoom -> reportRoom.getId() != null, ReportRoom::getRoomId);
                            return roomResponses.stream()
                                    .map(roomResponse -> roomResponse.setNumberReport(mapRoomWithNumberReport.getOrDefault(roomResponse.getId(), 0l)))
                                    .collect(Collectors.toList());
                        })));
    }

    private Single<List<String>> getFavoriteOfUser(Long userId) {
        if (userId == null) return Single.just(new ArrayList<>());
        return favoriteRoomRepository.getByUserId(userId)
                .map(favoriteRooms -> extractField(favoriteRooms, FavoriteRoom::getRoomId));
    }

    @Override
    public Single<List<RoomResponse>> getByIDS(List<String> roomIds) {
        return roomRepository.getByIds(roomIds)
                .map(roomMapper::roomResponses);
    }

    private Single<List<RoomResponse>> mapRoomOwner(List<Room> rooms, List<String> favoriteRoomIds) {
        List<Long> userIds = extractField(rooms, Room::getUserId);
        return userService.getByIds(userIds)
                .map(userResponses -> mapRoomResponse(rooms, userResponses))
                .map(roomResponses -> mapFavorite(favoriteRoomIds, roomResponses));
    }

    private static List<RoomResponse> mapFavorite(List<String> favoriteRoomIds, List<RoomResponse> roomResponses) {
        if (favoriteRoomIds == null || favoriteRoomIds.isEmpty()) return roomResponses;
        return roomResponses.stream()
                .map(roomResponse -> {
                    if (favoriteRoomIds.contains(roomResponse.getId()))
                        return roomResponse.setIsFavoriteRoom(true);
                    return roomResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Single<List<RoomResponse>> getByUserId(PersonalRoomRequest request, Pageable pageable) {
        return roomRepository.getByUserId(request, pageable)
                .flatMap(pair -> {
                    pageable.setTotal(pair.getLeft());
                    return mapRoomOwner(pair.getRight(), new ArrayList<>());
                });
    }

    @Override
    public Single<List<RoomResponse>> getAllPendingRoom(Pageable pageable) {
        return roomRepository.getByStatus(pageable)
                .flatMap(pair -> {
                    pageable.setTotal(pair.getLeft());
                    return mapRoomOwner(pair.getRight(), new ArrayList<>());
                });
    }

    private List<RoomResponse> mapRoomResponse(List<Room> rooms, List<UserResponse> userResponses) {
        if (rooms.isEmpty()) return new ArrayList<>();
        Map<Long, UserResponse> userResponseMap = collectToMap(userResponses, UserResponse::getId);
        return roomMapper.roomResponses(rooms)
                .stream()
                .map(r -> r.setOwnerInfo(userResponseMap.getOrDefault(r.getUserId(), new UserResponse())))
                .collect(Collectors.toList());
    }


}
