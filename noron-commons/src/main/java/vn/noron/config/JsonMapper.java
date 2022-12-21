package vn.noron.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.jooq.JSON;
import org.springframework.context.annotation.Configuration;
import vn.noron.core.json.serializer.JSONDeserializer;
import vn.noron.core.json.serializer.JSONSerializer;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@Configuration
public class JsonMapper {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) new JsonMapper().resetJsonConfig();
        return objectMapper;
    }

    public void resetJsonConfig() {
        objectMapper = new ObjectMapper();
        objectMapper
                .registerModule(new JavaTimeModule())
                .registerModule(JSONModule())
                .setPropertyNamingStrategy(SNAKE_CASE)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.registerModule(JSONModule());
    }

    public static SimpleModule JSONModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(JSON.class, new JSONDeserializer());
        simpleModule.addSerializer(JSON.class, new JSONSerializer());
        return simpleModule;
    }
}