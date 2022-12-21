package vn.noron.api.service.user;

import io.reactivex.rxjava3.core.Single;
import org.springframework.security.core.Authentication;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.user.ChangePasswordRequest;
import vn.noron.data.request.user.CreateUserRequest;
import vn.noron.data.request.user.FilterUserRequest;
import vn.noron.data.request.user.UpdateUserRequest;
import vn.noron.data.response.user.UserResponse;

import java.util.List;

public interface IUserService {
    Single<UserResponse> getMe(Long userId, Authentication authentication);

    Single<UserResponse> createUser(CreateUserRequest request);

    Single<UserResponse> banUserById(Long id);

    Single<UserResponse> unbanUserById(Long id);

    Single<UserResponse> getByEmail(String email);
    Single<UserResponse> updateUser(UpdateUserRequest request);

    Single<String> changePassword(ChangePasswordRequest request);

    Single<List<UserResponse>> getUserWithPageable(Pageable pageable, String keyword);

    Single<UserResponse> getDetailUser(Long id);
    Single<List<UserResponse>> getByIds(List<Long> ids);

    Single<String> deleteUser(Long id);
}
