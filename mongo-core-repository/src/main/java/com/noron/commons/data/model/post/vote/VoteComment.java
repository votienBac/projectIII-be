package com.noron.commons.data.model.post.vote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoteComment {
    @JsonProperty("_id")
    private String id;
    private String ownerId;
    private String ownerPost;
    private String ownerComment;
    private String postId;
    private String commentId;
    private Long creationTime;
    private Integer value; // 1 is vote, -1 is down vote
    private String from;
    private String type;
    private Boolean anonymous;
    private String historyVote; // kiểm tra user đã từng vote post này chưa. voted: đã từng vote, null: chưa từng vote
}