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
import vn.noron.api.service.fcm.IFcmService;
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.config.bind.annotation.PageableRequest;
import vn.noron.apiconfig.config.bind.annotation.SearchRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.apiconfig.model.DfResponseList;
import vn.noron.data.fcm.FcmTokenRequest;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.user.ChangePasswordRequest;
import vn.noron.data.request.user.UpdateUserRequest;
import vn.noron.data.response.user.UserResponse;
import vn.noron.data.tables.pojos.FcmTokenUser;
import vn.noron.utils.authentication.AuthenticationUtils;

import javax.validation.Valid;

import java.util.List;

import static vn.noron.apiconfig.model.DfResponseList.okEntity;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final IUserService userService;
    private final IFcmService fcmService;

    public UserController(IUserService userService, IFcmService fcmService) {
        this.userService = userService;
        this.fcmService = fcmService;
    }

    @Operation(summary = "Thông tin chi tiết user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin chi tiết User",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "bad-request", content = @Content),
    })
    @GetMapping("/me")
    public @NonNull Single<ResponseEntity<DfResponse<UserResponse>>> getMe(Authentication authentication) {
        Long userId = AuthenticationUtils.loggedUserId(authentication);
        return userService.getMe(userId, authentication)
                .map(DfResponse::okEntity);
    }


    @Operation(summary = "Chỉnh sửa tài khoản")
    @ApiResponse(responseCode = "200", description = "Chỉnh sửa tài khoản",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/update")
    public @NonNull Single<ResponseEntity<DfResponse<UserResponse>>> createUser(@RequestBody @Valid UpdateUserRequest request, Authentication authentication){
        return userService.updateUser(request.setUserId(AuthenticationUtils.loggedUserId(authentication)))
                .map(DfResponse::okEntity);
    }
    @Operation(summary = "Thay đổi mật khẩu ")
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công mật khẩu mới",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)

    @PostMapping(value = "/change-password")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request, Authentication authentication){
        request.setId(AuthenticationUtils.loggedUserId(authentication));
        return userService.changePassword(request)
                .map(DfResponse::okEntity);
    }

    @Operation(summary = "Thêm fisebase cloud message token")
    @ApiResponse(responseCode = "200", description = "Thêm fisebase cloud message token",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/add-fcm-token")
    public @NonNull Single<ResponseEntity<DfResponse<String>>> addFcmToken(@RequestBody @Valid FcmTokenRequest request, Authentication authentication){
        return fcmService.saveUserFcmToken(request.setUserId(AuthenticationUtils.loggedUserId(authentication)))
                .map(fcmTokenUser -> "success")
                .map(DfResponse::okEntity);
    }
    @Operation(summary = "Danh sách tài khoản theo email")
    @ApiResponse(responseCode = "200", description = "Liệt kê danh sách tài khoản theo email",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @PostMapping(value = "/get-by-email")
    public Single<ResponseEntity<DfResponse<List<UserResponse>>>> getByEmails(@RequestBody List<String> emails){
        return userService.getByEmails(emails)
                .map(DfResponse::okEntity);
    }

}
