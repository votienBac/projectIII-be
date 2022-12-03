package com.noron.commons.data.model.analytics;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageviewLog {
    private String objectId;
    private String objectType;
    private String fullUrl;
    private String hitUrl;
    private String prevPage;
    private String referrer;
    private Long creationTime;
}
