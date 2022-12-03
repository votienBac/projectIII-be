package com.noron.commons.data.model.post.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PostStatisticMonthly {
    @JsonProperty("_id")
    private String id; // postId-yyyy-mm
    private String postId;
    private String postType;
    private String sessionId;
    private String seriesId;
    private List<String> topicIds;
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
    private String ownerId;
}
