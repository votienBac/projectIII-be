package vn.noron.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import vn.noron.commons.config.database.mongo.NoronApiMongoConfig;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
//@EnableConfigurationProperties(NoronApiMongoConfig.class)
@ComponentScan("vn.noron")
public class NoronApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoronApiApplication.class, args);
    }

}
