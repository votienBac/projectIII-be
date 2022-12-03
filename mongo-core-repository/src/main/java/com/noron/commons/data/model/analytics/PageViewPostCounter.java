package com.noron.commons.data.model.analytics;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageViewPostCounter {
    @JsonProperty("_id")
    private String id;
    private Long numPageview;
    private String title;
    private String postType;
    private Long google;
}
