package com.noron.commons.data.model.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PostPageViewHourly {
    @JsonProperty("_id")
    private String id;
    private String fullDate;
    private Long numView;
    private String postId;
    private String postType;
    private String sessionId;
    private String seriesId;
    private String ownerId;
    private List<String> topicIds;
    private List<String> clientIds;
    private Long updatedAt;
}
