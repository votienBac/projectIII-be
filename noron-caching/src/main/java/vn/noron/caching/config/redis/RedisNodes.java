package vn.noron.caching.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisNodes {
    private List<String> nodes;
}
