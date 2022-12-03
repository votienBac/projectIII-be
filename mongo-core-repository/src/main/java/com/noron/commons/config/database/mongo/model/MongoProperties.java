package com.noron.commons.config.database.mongo.model;

import lombok.Data;

@Data
public class MongoProperties {
    private String uri;
    private String database;
}
