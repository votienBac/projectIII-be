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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.noron.api.service.user.IUserProfileService;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.data.request.AuthenticationRequest;
import vn.noron.data.response.user.AuthenticationResponse;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthenticationResource {
    private final IUserProfileService userProfileService;

    public AuthenticationResource(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Operation(summary = "Đăng nhập", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập vào hệ thống",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DfResponse.class))})
    })
    @PostMapping("/authenticate")
    public @NonNull Single<ResponseEntity<DfResponse<AuthenticationResponse>>> authentication(
            @RequestBody @Valid AuthenticationRequest request) {
        return userProfileService.authenticate(request)
                .map(DfResponse::okEntity);
    }
}
