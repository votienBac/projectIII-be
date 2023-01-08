package vn.noron.api.wio.rest;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.noron.api.service.backjob.BackjobService;
import vn.noron.api.service.room.IRoomService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.constant.room.RoomType;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.PersonalRoomRequest;
import vn.noron.data.request.room.SearchRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.utils.authentication.AuthenticationUtils;

import javax.validation.Valid;

import java.util.List;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/room")
public class RoomController {
    private final IRoomService roomService;
    private final BackjobService backjobService;

    public RoomController(IRoomService roomService, BackjobService backjobService) {
        this.roomService = roomService;

        this.backjobService = backjobService;
    }

    @Operation(summary = "Tạo phong")
    @ApiResponse(responseCode = "200", description = "Tạo phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/create")
    public @NonNull Single<ResponseEntity<DfResponse<Room>>> createUser(@RequestBody @Valid CreateRoomRequest request, Authentication authentication) {
        request.setUserId(AuthenticationUtils.loggedUserId(authentication))
                .setIsAdmin(AuthenticationUtils.isAdmin(authentication));
        return roomService.createRoom(request)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Tạo phong")
    @ApiResponse(responseCode = "200", description = "Tạo phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/add-data")
    public @NonNull Single<ResponseEntity<DfResponse<Boolean>>> addData() {
        return backjobService.addDataToDB()
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Sửa thông tin phòng")
    @ApiResponse(responseCode = "200", description = "Sua phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/update")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> createUser(@RequestBody @Valid UpdateRoomRequest request, Authentication authentication) {
        request.setUserId(AuthenticationUtils.loggedUserId(authentication))
                .setIsAdmin(AuthenticationUtils.isAdmin(authentication));
        return roomService.updateRoom(request)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Xóa phòng")
    @ApiResponse(responseCode = "200", description = "Xóa phòng",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)
    @DeleteMapping(value = "/delete/{id}")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> deleteUser(
            @Parameter(
                    name =  "id",
                    description = "User Id",
                    example = "1",
                    required = true)

            @PathVariable String id, Authentication authentication){
        return roomService.deleteRoom(id, authentication)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Thông tin chi tiết phòng ")
    @ApiResponse(responseCode = "200", description = "Sua phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping("/detail")
    public @NonNull Single<ResponseEntity<DfResponse<RoomResponse>>> getById( @RequestParam(value = "room_id") String roomId,
                                                                              Authentication authentication) {
        return roomService.roomDetail(roomId, AuthenticationUtils.loggedUserId(authentication))
                .map(DfResponse::okEntity);
    }


    @Operation(summary = "Tìm kiếm phòng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm kiếm phòng",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/search")
    public @NonNull Single<ResponseEntity<DfResponseList<RoomResponse>>> search(
            @PageableRequest Pageable pageable,
            @RequestBody SearchRoomRequest request,
            Authentication authentication) {
        request.setUserId(AuthenticationUtils.loggedUserId(authentication));
        return roomService.search(request, pageable)
                .map(users -> okEntity(users, pageable));
    }

    @Operation(summary = "Danh sách kiểu phòng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh sách kiểu phòng",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/room-type")
    public @NonNull Single<ResponseEntity<DfResponse<List<String>>>> getAllRoomType() {
        return Single.just(RoomType.getAll())
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Lấy tất cả phòng của user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy tất cả phòng của user",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/personal-room")
    public @NonNull Single<ResponseEntity<DfResponseList<RoomResponse>>> getPersonalRoom(
            @PageableRequest Pageable pageable,
            @RequestBody PersonalRoomRequest request,
            Authentication authentication) {
        request.setUserId(AuthenticationUtils.loggedUserId(authentication));
        return roomService.getByUserId(request, pageable)
                .map(users -> okEntity(users, pageable));
    }



}
