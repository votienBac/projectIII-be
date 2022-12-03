package com.noron.commons.data.model.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.noron.commons.data.log.ClientAgent;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ClientLogHourly {
    @JsonProperty("_id")
    private String id;
    private String clientId;
    private String userId;
    private Long updatedAt;
    private List<TimeOnsiteLog> timeOnSites;
    private List<PageviewLog> pageviews;
    private List<PostActionLog> scrollFeeds;
    private ClientAgent clientAgent;
}
