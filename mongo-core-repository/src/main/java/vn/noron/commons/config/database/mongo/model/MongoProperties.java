package vn.noron.commons.config.database.mongo.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
public class MongoProperties {
    private String uri;
    private String database;
}
