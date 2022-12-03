package vn.noron.data.model.user;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserRoleDetail {
    private Long roleId;
    private Long userId;
    private String name;
}
