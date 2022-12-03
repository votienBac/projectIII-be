package com.noron.commons.data.response.message;

import com.noron.commons.data.response.user.UserInfoResponse;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageResponse {
    private UserInfoResponse sender;
    private MessageInfoResponse message;
    private String timeStamp;
}
