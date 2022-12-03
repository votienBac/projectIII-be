package com.noron.commons.config.database.mongo;

import com.noron.commons.config.database.mongo.model.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class NoronApiMongoConfig {

    @Bean
    @ConfigurationProperties(prefix = "database.mongo.noron-api")
    public MongoProperties noronApiConfig() {
        return new MongoProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "database.mongo.training")
    public MongoProperties trainingConfig() {
        return new MongoProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "database.mongo.chat")
    public MongoProperties chatConfig() {
        return new MongoProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "database.mongo.analytics")
    public MongoProperties analyticsConfig() {
        return new MongoProperties();
    }
}
