package vn.noron.data.request.favoriteroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FavoriteRoomRequest {
    @JsonIgnore
    Long userId;
    String roomId;
}
