package com.noron.commons.data.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {
    @JsonProperty("_id")
    private String id;
    private String src;
    private String title;
    private String ownerId;
    private String type;
    private String coverUrl;
    private String description;
    private List<String> topicIds;
    private Integer status;
    private Integer numView = 0;
    private Integer numComment = 0;
    private Integer numLike = 0;
    private Integer numDislike = 0;
    private Long creationTime;
    private Long startTime;
    private Long endTime;
    private Long lastModifiedTime;
    private Long lastCommentAt;
    private Long deletedAt;
    private Long numCoin = 0L;
}
