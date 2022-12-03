package com.noron.commons.data.model.feed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserPostScore {
    @JsonProperty("_id")
    private String id; // user_id:post_id
    private String userId;
    private String postId;
    private Double score;
    private Integer order;
    private Double orderTemp;
    private String postType;
    private String explain;
    private String explainDetail;
    private Long postCreatedAt;
    private Long updatedAt;
}
