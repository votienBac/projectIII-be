package vn.noron.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Accessors(chain = true)
@Configuration
@ConfigurationProperties("momo")
public class MomoConfig {
    private String secretKey;
    private String accessKey;
    private String partnerCode;
    private String partnerName;
    private String redirectUrl;
    private String ipnUrl;
    private String orderInfo;
    private String requestType;
}
