package com.noron.commons.data.model.post.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PostStatisticDaily {
    @JsonProperty("_id")
    private String id; // postId-yyyy-mm-dd
    private String postId;
    private String sessionId;
    private String seriesId;
    private List<String> topicIds;
    private String postType;
    private Long numComment;
    private Long numAnswer;
    private Long numView;
    private Long numUserView;
    private Long numVote;
    private Long numUserVote;
    private Long creationTime;
    private Long numCoin;
    private Integer month;
    private Integer year;
    private String fullDate;
    private String ownerId;
}