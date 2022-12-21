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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.noron.api.service.reportroom.IReportRoomService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.favoriteroom.FavoriteRoomRequest;
import vn.noron.data.request.reportroom.ReportRoomRequest;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.utils.authentication.AuthenticationUtils;

import javax.validation.Valid;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/report-room")
public class ReportRoomController {
    private final IReportRoomService reportRoomService;

    public ReportRoomController(IReportRoomService reportRoomService) {
        this.reportRoomService = reportRoomService;
    }


    @Operation(summary = "Báo cáo phòng")
    @ApiResponse(responseCode = "200", description = "Báo cáo phòng",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/add")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> insert(@RequestBody @Valid ReportRoomRequest request, Authentication authentication){
        request.setUserId(AuthenticationUtils.loggedUserId(authentication));
        return reportRoomService.insert(request)
                .map(DfResponse::okEntity);
    }








}
