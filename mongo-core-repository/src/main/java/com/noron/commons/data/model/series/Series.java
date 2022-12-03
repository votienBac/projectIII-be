package com.noron.commons.data.model.series;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Series {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String ownerId;
    private Long deleted_at;
    private List<PostIndex> postIndices;
}
