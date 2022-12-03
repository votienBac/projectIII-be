package com.noron.commons.data.model.post.vote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VotePost {
    private String id;
    private String ownerId;
    private String ownerPost;
    private String postId;
    private List<String> topicIds;
    private Long creationTime;
    private Integer value; // 1 is vote, -1 is down vote
    private String from;
    private String historyVote; // kiểm tra user đã từng vote post này chưa. voted: đã từng vote, null: chưa từng vote
}
