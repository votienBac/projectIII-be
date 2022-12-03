package com.noron.commons.data.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MustComment {
    @JsonProperty("_id")
    private String id;
    private String postId;
    private String commentId;
    private String type;
    private Integer numComment;
    private Integer numMustComment;
    private String ownerId;
    private Integer status; // 0 not_done. 1 done. 2 ignore
    private List<String> topicIds;
    private String ignoreBy;
    private Long doneAt;
    private Long creationTime;
    private Long updatedAt;
    private Long sortField;
    private String operatorId;
    private List<UserComment> comments;
    private List<String> operatorIds;
}
