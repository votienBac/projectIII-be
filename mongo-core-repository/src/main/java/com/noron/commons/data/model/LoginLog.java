package com.noron.commons.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginLog {
    @JsonProperty("_id")
    private String id;
    private String userId;
    private Long creationTime;
}
