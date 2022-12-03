package com.noron.commons.data.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserComment {
    private String id;
    private String type;
    private String ownerId;
    private String parentId;
    private Long creationTime;
    private String operatorId;
}