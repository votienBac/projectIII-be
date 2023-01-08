package vn.noron.commons.config.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import vn.noron.commons.config.database.mongo.model.MongoProperties;
import org.bson.Document;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import vn.noron.commons.config.database.mongo.model.CollectionConstant;


@Component
public class MongoDriverCommon {
    private final MongoDatabase apiMongoDatabase;
    public MongoDriverCommon(MongoProperties noronApiConfig) {
        this.apiMongoDatabase = new MongoClient(new MongoClientURI("mongodb://motel-dev:27017/"))
                .getDatabase("motel");
    }

    @Bean
    @ConditionalOnMissingBean(name = "roomCollection")
    public MongoCollection<Document> roomCollection() {
        return apiMongoDatabase.getCollection(CollectionConstant.ROOM);
    }
//    @Bean
//    @ConditionalOnMissingBean(name = "messageCollection")
//    public MongoCollection<Document> messageCollection() {
//        return apiMongoDatabase.getCollection(CollectionConstant.MESSAGE);
//    }





}
