package com.noron.commons.data.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostRequest {
    private String coverUrl;
    private Integer publicStatus;
    private String postType;
    private String title;
    private String coverType;
    private String sharingCover;
    private String content;
    private List<String> tags;
    private Integer status;
    private List<String> linkedPostIds;
    private List<String> topicIds;
}
