package vn.noron.data.response.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String avatarUrl;
    @JsonIgnore
    private String password;
    List<String> roles;
    private Integer numberRoom;
    private Integer numBeReported;
}
