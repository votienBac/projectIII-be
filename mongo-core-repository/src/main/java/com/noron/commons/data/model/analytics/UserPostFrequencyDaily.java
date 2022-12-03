package com.noron.commons.data.model.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserPostFrequencyDaily {
    @JsonProperty("_id")
    private String id;
    private String postId;
    private String userId;
    private Long updatedAt;
    private String fullDate;
    private Integer numScrollFeed;
    private Integer numPageview;
}