package com.noron.commons.data.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserActivePoint {
    @JsonProperty("_id")
    private String id;
    private String userId;
    private Integer likePoint;
    private Integer viewPoint;
    private Integer questionPoint;
    private Integer commentPoint;
    private Integer answerPoint;
    private Integer blogPoint ;
    private Integer chatPoint;
    private Integer activePoint;
    private Long time;
}
