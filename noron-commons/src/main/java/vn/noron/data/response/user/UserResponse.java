package vn.noron.data.response.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.utils.time.CustomOffsetDateTimeDeSerializer;
import vn.noron.utils.time.CustomOffsetDateTimeSerializer;

import java.time.OffsetDateTime;
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
    String status;
    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    @JsonDeserialize(using = CustomOffsetDateTimeDeSerializer.class)
    OffsetDateTime createdAt;
    private Long numberRoom;
    private Long numberReported;
}
