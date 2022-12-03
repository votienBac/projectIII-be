package vn.noron.data.log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NoronLog {
    private String objectId;
    private Long ownerId;
    private Long creationTime;
    private String postId;
    private String sessionId;
    private String objectType;
    private String actionType;
    private Object clientInfo;
    private String clientId;
    private Map<String, Object> data;

    // client
    private String clientLogId;
    private String pageType;
    private String pageObjectId;
    private String hitUrl;
    private String fullUrl;
    private String prevPage;
    private String referrer;
    private Map<String, Object> extraData;
    private String ip;
    private String userAgent;
    private ClientAgent clientAgent;
    private Boolean isLanding;
    private String nrclid;
    private NoronPage noronPage;
    private ReferralData referralData;
}
