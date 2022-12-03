package com.noron.commons.data.model.analytics;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MyClipUrl {
    private String id;
    private String url;
    private String numView;
    private String channel;
    private Long updatedAt;
}