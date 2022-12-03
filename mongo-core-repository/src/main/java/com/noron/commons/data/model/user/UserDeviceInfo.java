package com.noron.commons.data.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.noron.commons.data.log.ClientAgent;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDeviceInfo {
    @JsonProperty("_id")
    private String id;
    private ClientAgent clientAgent;
    private Long loginTime;
    private String status;
    private String userId;
}
