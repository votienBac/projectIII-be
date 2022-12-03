package com.noron.commons.data.model.analytics;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageViewPostDaily {
    @JsonProperty("_id")
    private String id;
    private String postId;
    private Long numPageview;
    private Long google;
    private String postType;
    private Long updatedAt;
}
