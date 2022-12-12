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
import vn.noron.api.service.room.IRoomService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.config.bind.annotation.SearchRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.model.paging.Page;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;
import vn.noron.data.request.user.CreateUserRequest;
import vn.noron.data.response.user.UserResponse;
import vn.noron.utils.authentication.AuthenticationUtils;

import javax.validation.Valid;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/room")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;

    }

    @Operation(summary = "Tạo phong")
    @ApiResponse(responseCode = "200", description = "Tạo phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/create")
    public @NonNull Single<ResponseEntity<DfResponse<Room>>> createUser(@RequestBody @Valid CreateRoomRequest request, Authentication authentication){
        request.setUserId(AuthenticationUtils.loggedUserId(authentication))
                .setIsAdmin(AuthenticationUtils.isAdmin(authentication));
        return roomService.createRoom(request)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Sua phong")
    @ApiResponse(responseCode = "200", description = "Sua phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/update")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> createUser(@RequestBody @Valid UpdateRoomRequest request, Authentication authentication){
        request.setUserId(AuthenticationUtils.loggedUserId(authentication))
                .setIsAdmin(AuthenticationUtils.isAdmin(authentication));
        return roomService.updateRoom(request)
                .map(DfResponse::okEntity);
    }
    @Operation(summary = "Sua phong")
    @ApiResponse(responseCode = "200", description = "Sua phong",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/{id}")
    public @NonNull Single<ResponseEntity<DfResponse<Room>>> getById(@PathVariable String id){
        return roomService.getByID(id)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Tìm kiếm phong", tags = {"Admin-User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh sách room theo page",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/search")
    public @NonNull Single<ResponseEntity<DfResponseList<Room>>> search(
            @PageableRequest Pageable pageable,
            @SearchRequest String keyword) {
        return roomService.search(keyword,pageable)
                .map(users -> okEntity(users, pageable));
    }
}
