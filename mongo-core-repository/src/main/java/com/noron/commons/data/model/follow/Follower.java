package com.noron.commons.data.model.follow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Follower {
    @JsonProperty("_id")
    private String id;
    private String userId;
    private String followerId;
    private Long creationTime;
}
