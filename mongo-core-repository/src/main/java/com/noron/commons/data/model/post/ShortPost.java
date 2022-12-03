package com.noron.commons.data.model.post;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShortPost {
    @JsonProperty("_id")
    private String id;
    private String title;
    private String ownerId;
    private String postType;
    private Long creationTime;
}
