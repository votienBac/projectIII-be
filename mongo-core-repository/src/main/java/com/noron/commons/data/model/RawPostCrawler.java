package com.noron.commons.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RawPostCrawler {
    @JsonProperty("_id")
    private String id;
    private String ownerId;
    private Boolean isPosted;
    private String source;
    private PostRequest data;
}
