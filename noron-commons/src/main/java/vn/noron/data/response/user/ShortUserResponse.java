package vn.noron.data.response.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShortUserResponse {
    private Long id;
    private String fullName;
    private String about;
    private String avatarUrl;
    private String corverUrl;
    private String title;
    private String signature;
}
