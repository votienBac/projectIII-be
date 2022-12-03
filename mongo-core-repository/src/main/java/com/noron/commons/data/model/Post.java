package com.noron.commons.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noron.commons.data.dto.Media;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    @JsonProperty("_id")
    private String id;
    private Media media;
    private List<String> userVote;
    private List<String> userDownVote;
    private List<String> usersHidden;
    private List<String> usersBad;
    private List<String> userBookmark;
    private List<String> userMarkMorePost;
    private Long creationTime;
    private String title;
    private String content;
    private Integer numView = 0;
    private Integer numPageView = 0;
    private Integer numAnswer = 0;
    private Integer numComment = 0;
    private Integer numDiscussion = 0;
    private Integer numQuestionContext = 0;
    private Integer numFollower = 0;
    private Long numCoin = 0L;
    private Integer numUpVote = 0;
    private Integer numDownVote = 0;
    private Integer votePoint = 0;
    private String shortDescription;
    private String ownerId;
    private Integer status; // == 2 waiting to post
    private Long lastModifiedTime;
    private Long lastCommentAt;
    private Long lastHotCommentAt;
    private String postType;
    private String coverType;
    private String coverUrl;
    private Integer publicStatus; // =0 public
    private String sharingCover;
    private List<String> linkedPostIds;
    private List<String> mergedIds;
    private Long timePosting;
    private String targetId;
    private Long deletedBy;
    private String deleteReason;
    private Long deletedAt;
    private List<String> tags;

    private String sessionId;
    private String seriesId;
    private List<String> topicIds;
}
