package vn.noron.data.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.noron.data.request.user.CreateUserRequest;
import vn.noron.data.request.user.UpdateUserRequest;
import vn.noron.data.response.user.UserResponse;
import vn.noron.data.tables.pojos.User;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends AbsMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Named("toResponse")
    public abstract UserResponse toResponse(User user);

    @IterableMapping(qualifiedByName = "toResponse")
    public abstract List<UserResponse> toResponses(List<User> users);

    public abstract User toPOJO(CreateUserRequest source);

    public abstract User toPOJO(UpdateUserRequest source);

    @AfterMapping
    public void afterToPOJO(@MappingTarget User target, CreateUserRequest source) {
        target.setPassword(passwordEncoder.encode(source.getPassword()));
    }

}
