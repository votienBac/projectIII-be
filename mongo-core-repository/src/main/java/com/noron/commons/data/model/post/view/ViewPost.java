package com.noron.commons.data.model.post.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewPost {
    @JsonProperty("_id")
    private String id;
    private String userId;
    private String postId;
    private String postType;
    private Long lastViewPostTime;
    private Long postCreatedTime;
    private Long numView;
    private Integer status;
}