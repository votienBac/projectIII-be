package vn.noron.data.log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NoronPage {
    private String url;
    private String type;
    private String domain;
    private String topicId;
    private String postId;
    private String useId;
    private String seoPage;
    private String objectId;
    private String absoluteUrl;
    private String absoluteUrlWithParams;
    private Map<String, String> parameters;
}
