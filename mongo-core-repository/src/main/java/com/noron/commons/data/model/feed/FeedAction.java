package com.noron.commons.data.model.feed;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FeedAction {
    private String key;
    private String ownerId;
    private String postId;
    private String answerId;
    private String actionType;
    private String objectId;
    private String objectType;
    private Long actionTime;
    private String reason;
}
