package com.noron.commons.data.model.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SessionPost {
    @JsonProperty("_id")
    private String id;
    private String postId;
    private String seoId;
    private String targetUrl;
    private String title;
    private String description;
    private String coverUrl;
    private String type;
    private Long startTime;
    private Long endTime;
    private Integer status;
    private String ownerId;
}
