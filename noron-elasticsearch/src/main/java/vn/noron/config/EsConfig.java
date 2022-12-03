package vn.noron.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("noron.database.es")
public class EsConfig {
    private String username;
    private String password;
    private HttpHostConfig hostPort;
}
