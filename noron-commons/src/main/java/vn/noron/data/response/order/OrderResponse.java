package vn.noron.data.response.order;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;
import vn.noron.utils.time.CustomOffsetDateTimeDeSerializer;
import vn.noron.utils.time.CustomOffsetDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderResponse {
    private Long id;
    private Boolean isPaid;
    private Double total;
    private Long userId;
    private String roomId;
    private UserResponse owner;
    private RoomResponse roomResponse;
    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    @JsonDeserialize(using = CustomOffsetDateTimeDeSerializer.class)
    private OffsetDateTime createdAt;
}
