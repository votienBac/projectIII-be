package vn.noron.api.service.user;


import io.reactivex.rxjava3.core.Single;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import vn.noron.apiconfig.config.authentication.JwtService;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.data.constant.UserStatus;
import vn.noron.data.mapper.UserProfileMapper;
import vn.noron.data.request.AuthenticationRequest;
import vn.noron.data.response.user.AuthenticationResponse;
import vn.noron.data.tables.pojos.Role;
import vn.noron.data.tables.pojos.User;
import vn.noron.repository.user.IUserRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static vn.noron.core.template.RxTemplate.rxBlockingAsync;
import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.constant.MessageResponse.INVALID_USERNAME_PASS;
import static vn.noron.data.constant.MessageResponse.USER_INACTIVE;

@Service
public class UserProfileServiceImpl implements IUserProfileService {
    private final UserProfileMapper mapper;
    private final JwtService jwtService;
    private final IUserRepository userRepository;

    public UserProfileServiceImpl(UserProfileMapper mapper,
                                  JwtService jwtService,
                                  IUserRepository userRepository) {
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Single<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return Single
                .zip(
                        userRepository.findByUsername(request.getUsername()),
                        userRepository.getRolesByUsername(request.getUsername()),
                        Pair::of)
                .flatMap(pair -> checkValidAuthentication(request, pair))
                .map(pair -> {
                    final User user = pair.getLeft();
                    final String token = jwtService.generateJwt(user, pair.getRight());
                    return new AuthenticationResponse().setToken(token);
                });
    }


    @SneakyThrows
    private Single<Pair<User, List<Role>>> checkValidAuthentication(AuthenticationRequest loginRequest,
                                                                    Pair<Optional<User>, List<Role>> userRolePair) {
        if(userRolePair.getLeft().isPresent()){
            final User user = userRolePair.getLeft().get();
            return rxSchedulerIo(() -> {
                if (user.getStatus().equals(UserStatus.INACTIVE.getStatus()))
                    throw new ApiException(USER_INACTIVE);
                final boolean isValidPass = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
                if (!isValidPass) throw new ApiException(INVALID_USERNAME_PASS, UNAUTHORIZED.value());
                return Pair.of(userRolePair.getLeft().get(), userRolePair.getRight());
            });
        }else {
            throw new ApiException(INVALID_USERNAME_PASS, UNAUTHORIZED.value());
        }
    }
}
