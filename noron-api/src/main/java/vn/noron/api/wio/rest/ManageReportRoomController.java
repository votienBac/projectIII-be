package vn.noron.api.wio.rest;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.noron.api.service.reportroom.IReportRoomService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.config.bind.annotation.SearchRequest;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.response.reportroom.ReportRoomResponse;
import vn.noron.data.response.room.RoomResponse;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/admin/report-room")
public class ManageReportRoomController {

    private final IReportRoomService reportRoomService;

    public ManageReportRoomController(IReportRoomService reportRoomService) {
        this.reportRoomService = reportRoomService;
    }

    @Operation(summary = "Lấy tất cả bao cao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy tất cả bao cao",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportRoomResponse.class))}),
            @ApiResponse(responseCode = "404", description = "bad-request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/pageable")
    public @NonNull Single<ResponseEntity<DfResponseList<ReportRoomResponse>>> getALlReportRoom(
            @PageableRequest Pageable pageable,
            @SearchRequest String keyword) {
        return reportRoomService.getPageable(pageable,keyword)
                .map(reportRooms -> okEntity(reportRooms, pageable));
    }
}
