package vn.noron.caching.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import vn.noron.core.log.Logger;

import java.net.Socket;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Component
@Configuration
@EnableScheduling
public class JedisConfigCommon {
    private final Logger logger = Logger.getLogger(this.getClass());
    private boolean isNoChange;
    private String oldRedisStatus;
    private JedisCluster jedisCluster;
    private final RedisNodes redisNodes;
    private final RedisClusterConfig configModel;


    public JedisConfigCommon(RedisNodes redisNodes, RedisClusterConfig configModel) {
        this.redisNodes = redisNodes;
        this.configModel = configModel;
        this.isNoChange = true;
        this.oldRedisStatus = getStatus();
    }

    public Jedis getRedis() {
        return new Jedis(configModel.getHost(), configModel.getPort());
    }

    public JedisCluster getRedisCluster() {
        if (jedisCluster == null || !isNoChange) {
            Set<HostAndPort> hostAndPorts = redisNodes.getNodes()
                    .stream()
                    .filter(this::isPortInUse)
                    .map(node -> {
                        final String[] hostPort = node.split(":");
                        return new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1]));
                    })
                    .collect(toSet());
            jedisCluster = new JedisCluster(
                    hostAndPorts,
                    configModel.getConnectTimeout(),
                    configModel.getConnectTimeout(),
                    5,
                    configModel.getPoolConfig());
            isNoChange = true;
        }
        return jedisCluster;
    }

    private boolean isPortInUse(String node) {
        boolean result;
        try {
            final String[] hostPort = node.split(":");
            Socket s = new Socket(hostPort[0], Integer.parseInt(hostPort[1]));
            s.close();
            result = true;
        } catch (Exception e) {
            logger.error("[ERRORREDIS] , check post", e);
            result = false;
        }
        return (result);
    }

    @Scheduled(fixedDelay = 1000)
    public void checkRedisStatus() {
        final String currentStatus = getStatus();
        if (!currentStatus.equals(oldRedisStatus)) {
            isNoChange = false;
            oldRedisStatus = currentStatus;
            long numRedisDown = Arrays.stream(currentStatus.split("-")).filter("0"::equals).count();
            if (numRedisDown > 4) logger.error("[ERRORREDIS] , Số lượng redis down {}", numRedisDown);
            getRedisCluster();
        }
    }

    private String getStatus() {
        return redisNodes.getNodes()
                .stream()
                .map(node -> isPortInUse(node) ? "1" : "0")
                .collect(Collectors.joining("-"));
    }
}
