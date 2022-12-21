package vn.noron.data.response.reportroom;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.response.user.UserResponse;

@Data
@Accessors(chain = true)
public class ReportRoomResponse {
    Long id;
    String roomId;
    Long userId;
    UserResponse userInfo;
    String reason;
}
