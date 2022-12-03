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
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.data.request.user.ChangePasswordRequest;
import vn.noron.data.request.user.CreateUserRequest;
import vn.noron.data.request.user.UpdateUserRequest;
import vn.noron.data.response.user.UserResponse;
import vn.noron.utils.authentication.AuthenticationUtils;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Thông tin chi tiết user", tags = {"User"})
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

    @Operation(summary = "Tạo tài khoản")
    @ApiResponse(responseCode = "200", description = "Tạo thành công một tài khoản mới",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "bad-request", content = @Content)
    @ApiResponse(responseCode = "401", description = "un authenticated", content = @Content)
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    @ApiResponse(responseCode = "422", description = "Input invalidate", content = @Content)

    @PostMapping(value = "/create")
    public @NonNull Single<ResponseEntity<DfResponse<UserResponse>>> createUser(@RequestBody @Valid CreateUserRequest request){
        return userService.createUser(request)
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
}
