package vn.noron.api.wio.rest;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.noron.api.service.reportroom.IReportRoomService;
import vn.noron.api.service.room.IRoomService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.response.reportroom.ReportRoomResponse;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.utils.authentication.AuthenticationUtils;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/admin/room")
public class ManageRoomController {
    private final IRoomService roomService;
    private final IReportRoomService reportRoomService;

    public ManageRoomController(IRoomService roomService, IReportRoomService reportRoomService) {
        this.roomService = roomService;
        this.reportRoomService = reportRoomService;
    }

    @Operation(summary = "Lấy tất cả phòng chờ duyệt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy tất cả phòng chờ duyệt",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/pending-room")
    public @NonNull Single<ResponseEntity<DfResponseList<RoomResponse>>> getALlPendingRoom(
            @PageableRequest Pageable pageable) {
        return roomService.getAllPendingRoom(pageable)
                .map(users -> okEntity(users, pageable));
    }

    @Operation(summary = "Duyệt phòng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duyệt phòng",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/censor/{id}")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> censorshipRoom(
            @PathVariable String id) {
        return roomService.censorshipRoom(id)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Danh sách report của phòng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh sách report của phòng",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/report")
    public @NonNull Single<ResponseEntity<DfResponseList<ReportRoomResponse>>> getAllReportOfRoom(
            @PageableRequest Pageable pageable,
            @RequestParam(value = "room_id") String roomId) {
        return reportRoomService.getAllReportOfRoom(roomId, pageable)
                .map(users -> okEntity(users, pageable));
    }
}
