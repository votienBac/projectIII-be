package vn.noron.data.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper extends AbsMapper {
//    public abstract UserResponse toResponse(User user);
//
//    public UserLoginResponse toResponse(User user, String token) {
//        final UserResponse userResponse = toResponse(user);
//        return new UserLoginResponse()
//                .setData(userResponse)
//                .setToken(token)
//                .setSetting(new HomePageSetting().setFeedType("hot"));
//    }
}
