package com.noron.commons.data.model.session;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Session {
    @JsonProperty("_id")
    private String id;
    private Long publicTime;
    private Long closeTime;
    private Long startTime;
    private Long endTime;
    private String ownerOverview;
    private String ownerId;
    private String coverUrl;
    private String previewCover;
    private String content;
    private Integer status;
    private String ownerQuote;
    private String sharingCover;
    private String expertName;
    private String expertTitle;
    private List<String> topicIds;
    private List<Long> followerIds;
    private Boolean opened;
    private Boolean remindAnswer;
    private Boolean remindCloseAsk;
    private Boolean closed;
    private Boolean thankExpert;
}