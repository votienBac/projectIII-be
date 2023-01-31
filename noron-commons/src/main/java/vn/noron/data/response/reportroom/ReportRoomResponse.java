package vn.noron.data.response.reportroom;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;
import vn.noron.utils.time.CustomOffsetDateTimeDeSerializer;
import vn.noron.utils.time.CustomOffsetDateTimeSerializer;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class ReportRoomResponse {
    Long id;
    String roomId;
    RoomResponse roomResponse;
    Long userId;
    UserResponse userInfo;
    String reason;
    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    @JsonDeserialize(using = CustomOffsetDateTimeDeSerializer.class)
    OffsetDateTime createdAt;
}
