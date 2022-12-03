package com.noron.commons.data.model.analytics;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PostActionLog {
    private Long time;
    private String postId;
}
