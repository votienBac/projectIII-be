package com.noron.commons.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FollowPost {
    @JsonProperty("_id")
    private String id;
    private String userId;
    private String postId;
    private String postType;
    private Long creationTime;
    private String from;
}
