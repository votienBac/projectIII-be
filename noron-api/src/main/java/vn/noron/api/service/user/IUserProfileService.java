package vn.noron.api.service.user;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.request.AuthenticationRequest;
import vn.noron.data.response.user.AuthenticationResponse;

public interface IUserProfileService {
    Single<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
