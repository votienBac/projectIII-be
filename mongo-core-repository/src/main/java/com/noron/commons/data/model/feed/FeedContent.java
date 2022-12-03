package com.noron.commons.data.model.feed;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class FeedContent {
    private String userId;
    private String postId;
    private Double score;
    private Integer order;
    private Double orderTemp;
    private String postType;
    private String explain;
    private List<String> actionKeys;
    private String followReason;
    private String explainDetail;
    private Long postCreatedAt;
    private Long scoreTime;
    private Long updatedAt;
    private String hotCommentId;
    private Long hotCommentAt;
    private Long lastCommentAt;
    private Long sortTime;
    private List<String> topicIds = new ArrayList<>();
    private FeedAction feedAction;
    private Boolean isFollowedTopic;
    private Boolean isFollowedUser;
    private String followedTopic;
    private String key;
}