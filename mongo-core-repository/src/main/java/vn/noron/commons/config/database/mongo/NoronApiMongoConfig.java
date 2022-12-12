package vn.noron.commons.config.database.mongo;

import vn.noron.commons.config.database.mongo.model.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
//@ConfigurationProperties(prefix = "database.mongo.motel")
public class NoronApiMongoConfig {

    @Bean
    @ConfigurationProperties(prefix = "database.mongo.motel")
    public MongoProperties noronApiConfig() {
        return new MongoProperties();
    }

}
