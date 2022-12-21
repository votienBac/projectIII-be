package vn.noron.data.request.room;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PersonalRoomRequest {
    Long userId;
    Boolean isPending;
}
