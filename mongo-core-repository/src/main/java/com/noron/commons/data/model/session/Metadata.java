package com.noron.commons.data.model.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Metadata {
    @JsonProperty("_id")
    private String id;
    private String sessionId;
    private Integer order;
    private String mediaType;
    private String mediaUrl;
    private String mediaCover;
    private Long creationTime;
    private Long deletedAt;
}
