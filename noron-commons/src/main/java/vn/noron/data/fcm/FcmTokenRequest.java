package vn.noron.data.fcm;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FcmTokenRequest {
    Long userId;
    String token;
}
