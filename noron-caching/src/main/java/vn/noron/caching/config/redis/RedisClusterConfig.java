package vn.noron.caching.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisClusterConfig {
    private int connectTimeout;
    private List<RedisNodes> nodes;
    private JedisPoolConfig poolConfig;
    private String password;
    private String host;
    private Integer port;
}
