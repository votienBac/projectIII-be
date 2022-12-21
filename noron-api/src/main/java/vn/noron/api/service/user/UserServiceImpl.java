package vn.noron.api.service.user;


import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.core.exception.DBException;
import vn.noron.data.constant.UserStatus;
import vn.noron.data.mapper.UserMapper;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.user.UserRoleDetail;
import vn.noron.data.request.user.ChangePasswordRequest;
import vn.noron.data.request.user.CreateUserRequest;
import vn.noron.data.request.user.FilterUserRequest;
import vn.noron.data.request.user.UpdateUserRequest;
import vn.noron.data.response.user.UserResponse;
import vn.noron.data.tables.pojos.Role;
import vn.noron.data.tables.pojos.User;
import vn.noron.data.tables.pojos.UserRole;
import vn.noron.repository.role.IRoleRepository;
import vn.noron.repository.role.IUserRoleRepository;
import vn.noron.repository.user.IUserRepository;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static vn.noron.data.constant.Constant.invalidExceptionCode;
import static vn.noron.utils.CollectionUtils.filterList;

@Service
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final DSLContext dslContext;


    public UserServiceImpl(UserMapper userMapper,
                           IUserRepository userRepository,
                           IRoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           IUserRoleRepository userRoleRepository,
                           DSLContext dslContext) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.dslContext = dslContext;
    }


    @Override
    public Single<UserResponse> getMe(Long userId, Authentication authentication) {
        return getById(userId)
                .map(userMapper::toResponse)
                .map(userResponse -> userResponse.setRoles(getRolesForUser(authentication)));
    }

    public Single<User> getById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (user.isEmpty()) throw new DBException("Resource not found!");
                    return user.get();
                });
    }

    /**
     * Get role of current user with authentication
     *
     * @param authentication
     * @return
     */
    private List<String> getRolesForUser(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(item -> item.getAuthority().split("_")[1])
                .collect(Collectors.toList());
    }

    /**
     * Get role of user with user_id
     *
     * @param userId
     * @return
     */
    private List<String> getRolesForUser(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .map(userRoles -> userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()))
                .flatMap(roleRepository::findAllById)
                .map(roles -> roles.stream().map(Role::getName).collect(Collectors.toList())).blockingGet();
    }

    @Override
    public Single<UserResponse> createUser(CreateUserRequest request) {
        return Single.zip(
                        checkAlreadyAccount(request),
                        roleRepository.getByNames(request.getRoles()),
                        (user, roles) -> {
                            var response = new Object() {
                                User user = null;
                            };
                            dslContext.transaction(outer -> {
                                Optional<User> userOptional;
                                final DSLContext context = DSL.using(outer);
                                userOptional = userRepository.insertRetuningBlocking(user, context);
                                insertRole(mapRoleToUserRole(roles), userOptional, context);
                                response.user = userOptional.orElse(null);
                            });
                            return response.user;
                        })
                .map(userMapper::toResponse);
    }

    public List<UserRole> mapRoleToUserRole(List<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .map(id -> {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(id);
                    return userRole;
                })
                .collect(Collectors.toList());
    }


    private Single<User> checkAlreadyAccount(CreateUserRequest request) {
        return Single.zip(
                userRepository.findByUsername(request.getUsername()),
                userRepository.findByEmail(request.getEmail()),
                (userByName, userByEmail) -> {
                    Map<String, Object> data = new HashMap<>();
                    if (userByEmail.isPresent()) {
                        data.put("email", "Email already exist!");
                        throw new ApiException(invalidExceptionCode, data);
                    }
                    if (userByName.isPresent()) {
                        data.put("username", "Username already exist!");
                        throw new ApiException(invalidExceptionCode, data);
                    }
                    return userMapper.toPOJO(request);
                });


    }

    private void insertRole(List<UserRole> userRoles, Optional<User> optionalUser, DSLContext context) {
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<UserRole> listPOJO = userRoles.stream()
                    .peek(userRole -> userRole.setUserId(user.getId()))
                    .collect(Collectors.toList());
            userRoleRepository.insertBlocking(listPOJO, context);
        }
    }


    @Override
    public Single<UserResponse> updateUser(UpdateUserRequest request) {
        return checkExistsAccountById(request.getUserId())
                .flatMap(user -> checkAlreadyAccount(request, request.getUserId()))
                .flatMap(user -> userRepository.update(request.getUserId(), user))
                .flatMap(integer -> userRepository.findById(request.getUserId()))
                .map(optional -> userMapper.toResponse(optional.get()));
    }

    @Override
    public Single<UserResponse> banUserById(Long id) {
        return checkExistsAccountById(id)
                .flatMap(user -> {
                    user.setBanAt(OffsetDateTime.now());
                    user.setStatus(UserStatus.INACTIVE.getStatus());
                    return userRepository.update(id, user);
                }).flatMap(integer -> userRepository.findById(id)).map(user -> userMapper.toResponse(user.get()));
    }

    @Override
    public Single<UserResponse> unbanUserById(Long id) {
        return checkExistsAccountById(id)
                .flatMap(user -> {
                    user.setBanAt(null);
                    user.setStatus(UserStatus.ACTIVE.getStatus());

                    return userRepository.update(id, user);
                })
                .flatMap(integer -> userRepository.findById(id))
                .map(user -> userMapper.toResponse(user.get()));
    }

    public Single<User> checkExistsAccountById(Long id) {
        return userRepository.findById(id)
                .map(optional -> {
                    if (optional.isEmpty()) {
                        throw new ApiException("Account not exists");
                    }
                    return optional.get();
                });
    }


    @Override
    public Single<UserResponse> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(Optional::get)
                .map(userMapper::toResponse);
    }

    @Override
    public Single<String> changePassword(ChangePasswordRequest request) {
        return userRepository.findById(request.getId())
                .map(user -> {
                    if (user.isEmpty()) Single.error(new DBException("Resource not found"));
                    return user.get();
                })
                .flatMap(user -> {
                    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                        return Single.error(new ApiException("Wrong password"));
                    }
                    return Single.just(user);
                })
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    user.setUpdatedAt(OffsetDateTime.now());
                    return Single.just(user);
                })
                .flatMap(user -> userRepository.update(request.getId(), user))
                .map(integer -> integer == 1 ? "SUCCESS" : "FAIL");
    }
    public Single<Map<Long, List<String>>> getUserIdRoleNameMap(List<Long> userIds) {
        return userRoleRepository.findDetailByUserIds(userIds)
                .map(userRoleDetails -> toRoleNameMap(userIds, userRoleDetails));
    }
    private Map<Long, List<String>> toRoleNameMap(List<Long> userIds, List<UserRoleDetail> userRoleDetails) {
        return userIds.stream()
                .collect(toMap(
                        userId -> userId,
                        userId -> filterList(
                                userRoleDetails,
                                userRoleDetail -> userRoleDetail.getUserId().equals(userId))
                                .stream()
                                .map(userRoleDetail -> userRoleDetail.getName())
                                .collect(Collectors.toList())));
    }
    @Override
    public Single<List<UserResponse>> getUserWithPageable(Pageable pageable, String keyword) {
        return Single.zip(
                        userRepository.filterAndSearchCount(keyword),
                        userRepository.filterAndPageable(keyword, pageable),
                        ((total, users) -> {
                            pageable.setTotal(total);
                            return users;
                        }))
                .map(userMapper::toResponses);
    }

    @Override
    public Single<UserResponse> getDetailUser(Long id) {

        return Single.zip(
                getById(id),
                getUserIdRoleNameMap(Collections.singletonList(id)),
                (user, userIdRoleNameMap) ->
                {
                    UserResponse detailUserResponse = userMapper.toResponse(user);
                    detailUserResponse.setRoles(userIdRoleNameMap.get(id));
                    return detailUserResponse;
                }
        );
    }

    @Override
    public Single<List<UserResponse>> getByIds(List<Long> ids) {
        return userRepository.findAllById(ids)
                        .map(users -> userMapper.toResponses(users));

    }

    @Override
    public Single<String> deleteUser(Long id) {
        return checkExistsAccountById(id)
                .flatMap(user -> userRepository.deletedById(id).
                        map(integer -> "DELETE SUCCESS"));
    }


//    public List<UserRole> mapRoleToUserRole(List<Role> roles) {
//        return roles.stream()
//                .map(Role::getId)
//                .map(id -> {
//                    UserRole userRole = new UserRole();
//                    userRole.setRoleId(id);
//                    return userRole;
//                })
//                .collect(Collectors.toList());
//    }

    private Single<User> checkAlreadyAccount(UpdateUserRequest request, Long userId) {
        return Single.zip(
                        userRepository.findByUsername(request.getUsername()),
                        userRepository.findByEmail(request.getEmail()),
                        Pair::of
                )
                .flatMap(pair -> {
                    if (pair.getRight().isPresent() && !pair.getRight().get().getId().equals(userId)) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("email", "Email already taken!");
                        return Single.error(new ApiException(invalidExceptionCode, data));
                    }
                    if (pair.getLeft().isPresent() && !pair.getLeft().get().getId().equals(userId)) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("username", "Username already taken!");
                        return Single.error(new ApiException(invalidExceptionCode, data));
                    }
                    return Single.just(userMapper.toPOJO(request));
                });
    }
}
