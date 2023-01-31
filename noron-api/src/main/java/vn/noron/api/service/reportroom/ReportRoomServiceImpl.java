package vn.noron.api.service.reportroom;

import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.api.service.room.IRoomService;
import vn.noron.api.service.user.IUserService;
import vn.noron.data.mapper.reportroom.ReportRoomMapper;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.reportroom.ReportRoomRequest;
import vn.noron.data.response.reportroom.ReportRoomResponse;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;
import vn.noron.data.tables.pojos.ReportRoom;
import vn.noron.repository.reportroom.IReportRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static vn.noron.utils.CollectionUtils.*;

@Service
public class ReportRoomServiceImpl implements IReportRoomService {

    private final IReportRoomRepository reportRoomRepository;
    private final IRoomService roomService;
    private final ReportRoomMapper reportRoomMapper;
    private final IUserService userService;

    public ReportRoomServiceImpl(IReportRoomRepository reportRoomRepository, IRoomService roomService, ReportRoomMapper reportRoomMapper, IUserService userService) {
        this.reportRoomRepository = reportRoomRepository;
        this.roomService = roomService;
        this.reportRoomMapper = reportRoomMapper;
        this.userService = userService;
    }

    @Override
    public Single<String> insert(ReportRoomRequest request) {
        return reportRoomRepository.insert(reportRoomMapper.toPOJO(request))
                .map(integer -> "success");
    }

    @Override
    public Single<List<ReportRoomResponse>> getPageable(Pageable pageable, String keyword) {
        return Single.zip(
                        reportRoomRepository.filterAndSearchCount(keyword),
                        reportRoomRepository.filterAndPageable(keyword, pageable),
                        ((total, reportRooms) -> {
                            pageable.setTotal(total);
                            return reportRooms;
                        }))
                .flatMap(reportRooms -> Single.zip(
                        mapUserInfo(reportRooms),
                        roomService.getByIDS(extractField(reportRooms, ReportRoom::getRoomId)),
                        (reportRoomResponses, roomResponses) -> {
                            Map<String, RoomResponse> roomResponseMap = collectToMap(roomResponses, RoomResponse::getId);
                            return reportRoomResponses.stream()
                                    .map(reportRoomResponse -> reportRoomResponse.setRoomResponse(roomResponseMap.getOrDefault(reportRoomResponse.getRoomId(), null)))
                                    .collect(Collectors.toList());
                        }
                ));

    }

    @Override
    public Single<List<ReportRoomResponse>> getAllReportOfRoom(String roomId, Pageable pageable) {
        return reportRoomRepository.getByRoomId(roomId, pageable)
                .flatMap(pair -> {
                    pageable.setTotal(pair.getLeft());
                    return mapUserInfo(pair.getRight());
                });
    }

    private Single<List<ReportRoomResponse>> mapUserInfo(List<ReportRoom> rooms) {
        List<Long> userIds = extractField(rooms, ReportRoom::getUserId);
        return userService.getByIds(userIds)
                .map(userResponses -> mapRoomResponse(rooms, userResponses));
    }

    private List<ReportRoomResponse> mapRoomResponse(List<ReportRoom> reportRooms, List<UserResponse> userResponses) {
        if (reportRooms.isEmpty()) return new ArrayList<>();
        Map<Long, UserResponse> userResponseMap = collectToMap(userResponses, UserResponse::getId);
        return reportRoomMapper.roomResponses(reportRooms)
                .stream()
                .map(r -> r.setUserInfo(userResponseMap.getOrDefault(r.getUserId(), new UserResponse())))
                .collect(Collectors.toList());
    }
}
