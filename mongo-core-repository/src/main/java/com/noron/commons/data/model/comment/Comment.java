package com.noron.commons.data.model.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noron.commons.data.dto.Media;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

import static com.noron.commons.data.constant.GenericFieldConstant._ID;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    @JsonProperty(_ID)
    private String id;

    private Long creationTime;
    private Integer numComment = 0;
    private Integer votePoint = 0;
    private Integer numUpVote = 0;
    private Integer numDownVote = 0;
    private Integer numChildren = 0;

    private String coverUrl;
    private String coverType;
    private String shortDescription;
    private String fullSlug;
    private String slug;
    private Boolean postDeleted;
    private Boolean expertAnswer = false;
    private Boolean ancestorDeleted;
    private String postType;
    private Integer publicStatus;
    private String linkPostId;

    private String postId;
    private String sessionId;
    private String content;
    private String contentJson;
    private String ownerId;
    private String ownerName;
    private Long lastModifiedTime;
    private String parentId;
    private List<Comment> children;
    private String type;
    private String topicId;
    private List<String> topicIds;
    private Long timePosting;
    // answer-fee
    private String fullContent;
    private Boolean isAnswerFee;
    private Integer numCoinUnlock;
    private Double rating;
    private Integer numRate;
    private Integer numUnlock;
    private Integer verified;
    //
    private Media media;
    private List<String> upVote = new ArrayList<>();
    private List<String> downVote = new ArrayList<>();
    //
    private Long deletedBy;
    private String deleteReason;
    private Long deletedAt;
}