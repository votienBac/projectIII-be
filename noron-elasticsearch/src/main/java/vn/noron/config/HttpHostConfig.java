package vn.noron.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("noron.database.es.node")
public class HttpHostConfig {
    private String host;
    private Integer port;
    private String schema;
}
