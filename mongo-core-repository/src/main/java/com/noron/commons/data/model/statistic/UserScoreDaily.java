package com.noron.commons.data.model.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserScoreDaily {
    @JsonProperty("_id")
    private String id;
    private String date;
    private String userId;
    private String numView;
}
