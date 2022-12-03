package vn.noron.data.response.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginResponse {
    private String token;
    private UserResponse data;
    private HomePageSetting setting;
}
