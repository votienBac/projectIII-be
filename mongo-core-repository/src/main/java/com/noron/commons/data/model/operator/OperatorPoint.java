package com.noron.commons.data.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatorPoint {
    @JsonProperty("_id")
    private String id;
    private Long time;
    private String label;
    private String operatorId;
    private Integer point;
    private Boolean isActive;
    private Integer pointActive;
    private String mission;
}
