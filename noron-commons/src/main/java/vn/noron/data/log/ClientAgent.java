package vn.noron.data.log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonNaming(SnakeCaseStrategy.class)
public class ClientAgent {
    private String agentRaw;
    private String platform;
    private String operatingSystemName;
    private String operatingSystemVersion;
    private String deviceName;
    private String deviceVersion;
    private String agentName;
    private String agentVersion;
    private String appVersion;
    private String ipAddress;
}
