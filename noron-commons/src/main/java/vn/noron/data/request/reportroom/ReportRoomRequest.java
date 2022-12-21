package vn.noron.data.request.reportroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportRoomRequest {
    @JsonIgnore
    Long userId;
    String roomId;
    String reason;
}
