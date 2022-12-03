package vn.noron.api.wio.rest;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.config.bind.annotation.SearchRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.user.FilterUserRequest;
import vn.noron.data.response.user.UserResponse;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/admin/user")
public class ManageUserController {

    private final IUserService userService;

    public ManageUserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Danh sách tài khoản phân trang")
    @ApiResponse(responseCode = "200", description = "Liệt kê danh sách tài khoản phân trang",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @PostMapping(value = "/pageable")
    public Single<ResponseEntity<DfResponseList<UserResponse>>> getByPageable(
            @PageableRequest Pageable pageable,
            @SearchRequest String keyword){
        return userService.getUserWithPageable(pageable, keyword)
                .map(users -> okEntity(users, pageable));
    }
    @Operation(summary = "Thông tin chi  tài khoản")
    @ApiResponse(responseCode = "200", description = "Thông tin chi  tài khoản",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @GetMapping(value = "/detail/{id}")
    public @NonNull Single<ResponseEntity<DfResponse<UserResponse>>> detailUser(@PathVariable Long id){
        return userService.getDetailUser(id)
                .map(DfResponse::okEntity);
    }
    @Operation(summary = "Xóa tài khoản")
    @ApiResponse(responseCode = "200", description = "Xóa tài khoản",
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

            @PathVariable Long id){
        return userService.deleteUser(id)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Khóa tài khoản")
    @ApiResponse(responseCode = "200", description = "Khóa một tài khoản",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @PostMapping(value = "/ban/{id}")
    public @NonNull Single<ResponseEntity<DfResponse<UserResponse>>> banUser(@PathVariable Long id){
        return userService.banUserById(id)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Mở khóa tài khoản")
    @ApiResponse(responseCode = "200", description = "Mở khóa một tài khoản",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @PostMapping(value = "/unban/{id}")
    public @NonNull Single<ResponseEntity<DfResponse<UserResponse>>> unbanUser(@PathVariable Long id){
        return userService.unbanUserById(id)
                .map(DfResponse::okEntity);
    }
}
