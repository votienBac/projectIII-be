package com.noron.commons.data.model.series;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostIndex {
    private String postId;
    private String title;
    private Integer index;
}
