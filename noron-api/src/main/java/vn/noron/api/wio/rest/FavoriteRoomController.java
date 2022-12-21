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
import vn.noron.api.service.favoriteroom.IFavoriteRoomService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.constant.room.RoomType;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.favoriteroom.FavoriteRoomRequest;
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
@RequestMapping(value = "/api/v1/favorite-room")
public class FavoriteRoomController {
    private final IFavoriteRoomService favoriteRoomService;

    public FavoriteRoomController(IFavoriteRoomService favoriteRoomService) {
        this.favoriteRoomService = favoriteRoomService;
    }


    @Operation(summary = "Thêm vào danh sách quan tâm")
    @ApiResponse(responseCode = "200", description = "Thêm vào danh sách quan tâm",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/add")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> insert(@RequestBody @Valid FavoriteRoomRequest request, Authentication authentication){
        request.setUserId(AuthenticationUtils.loggedUserId(authentication));
        return favoriteRoomService.insert(request)
                .map(DfResponse::okEntity);
    }
    @Operation(summary = "Xóa khỏi danh sách quan tâm")
    @ApiResponse(responseCode = "200", description = "Xóa khỏi danh sách quan tâm",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/delete")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> delete(@RequestBody @Valid FavoriteRoomRequest request, Authentication authentication){
        request.setUserId(AuthenticationUtils.loggedUserId(authentication));
        return favoriteRoomService.delete(request)
                .map(DfResponse::okEntity);
    }




    @Operation(summary = "Danh sách phòng quan tâm của user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh sách phòng quan tâm của user",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/all")
    public @NonNull Single<ResponseEntity<DfResponseList<RoomResponse>>> getPersonalFR(
            @PageableRequest Pageable pageable,
            Authentication authentication) {
        return favoriteRoomService.getAllByUserId(AuthenticationUtils.loggedUserId(authentication), pageable)
                .map(users -> okEntity(users, pageable));
    }


}
